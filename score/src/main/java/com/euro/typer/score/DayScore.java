package com.euro.typer.score;

import com.euro.typer.data.source.dao.DailyResultDao;
import com.euro.typer.data.source.dao.DailyResultPackDao;
import com.euro.typer.data.source.dao.MatchDao;
import com.euro.typer.data.source.dao.PlayerTypeDao;
import com.euro.typer.data.source.entity.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.util.*;

/**
 * Created by miras108 on 2016-05-21.
 */
public class DayScore {
    private PlayerTypeDao playerTypeDao;
    private MatchDao matchDao;
    private DailyResultDao dailyResultDao;
    private DailyResultPackDao dailyResultPackDao;

    public static void main(String[] args) throws ParseException {
        DayScore p = new DayScore();
        p.loadBeans(args);
        p.createDayScore();
        p.printDayScore();
        p.printOverallScore();
    }

    private void loadBeans(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:dao-config.xml");

        playerTypeDao = (PlayerTypeDao) context.getBean("playerTypeDao");
        matchDao = (MatchDao) context.getBean("matchDao");
        dailyResultDao = (DailyResultDao) context.getBean("dailyResultDao");
        dailyResultPackDao = (DailyResultPackDao) context.getBean("dailyResultPackDao");
    }

    private void printOverallScore()
    {
        List<DailyResult> dailyResults = dailyResultDao.getAll();
        Map<Player, Double> playerPointsMap = new HashMap<Player, Double>();

        for(DailyResult dailyResult : dailyResults)
        {
            if(!playerPointsMap.containsKey(dailyResult.getPlayer()))
            {
                playerPointsMap.put(dailyResult.getPlayer(), 0.0);
            }

            Double currentPoints = playerPointsMap.get(dailyResult.getPlayer());
            playerPointsMap.put(dailyResult.getPlayer(), currentPoints + dailyResult.getPoints());
        }

        System.out.println("");
        System.out.println("Overall:");
        for(Map.Entry<Player, Double> entry : playerPointsMap.entrySet())
        {
            System.out.println(entry.getKey().getUsername() + ": " + entry.getValue() + "pts");
        }
    }

    private void printDayScore() throws ParseException {
        DailyResultPack dailyResultPack = dailyResultPackDao.getByDate(new Date());

        System.out.println("");
        System.out.println("Daily:");
        for(DailyResult dailyResult : dailyResultPack.getDailyResults())
        {
            System.out.println(dailyResult.getPlayer().getUsername() + ": " + dailyResult.getPoints() + "pts");
        }
    }

    private void createDayScore() throws ParseException {
        if(dailyResultPackDao.getByDate(new Date()) != null)
        {
            System.out.println("Daily results already generated");
            return;
        }

        List<Match> dayMatchList = matchDao.getMatchByDate(new Date());
        List<PlayerType> playerTypeList = playerTypeDao.getByMatchList(dayMatchList);

        Map<Player, List<PlayerType>> playerPlayerTypeListMap = getPlayerListMap(playerTypeList);

        List<DailyResult> dailyResults = new ArrayList<DailyResult>();
        for(Map.Entry<Player, List<PlayerType>> entry : playerPlayerTypeListMap.entrySet())
        {
            int points = 0;
            for(PlayerType playerType : entry.getValue())
            {
                points += ScoreResultCalculator.calculateScore(playerType.getTypedScore(), playerType.getMatch().getScore());
            }

            DailyResult dailyResult = new DailyResult();
            dailyResult.setPlayer(entry.getKey());
            dailyResult.setPlayerTypes(entry.getValue());
            dailyResult.setPoints(new Double(points));
            dailyResults.add(dailyResult);
        }
        dailyResultDao.saveDailyResults(dailyResults);

        DailyResultPack dailyResultPack = new DailyResultPack();
        dailyResultPack.setDailyResults(dailyResults);
        dailyResultPack.setDate(new Date());
        dailyResultPackDao.save(dailyResultPack);

    }

    private Map<Player, List<PlayerType>> getPlayerListMap(List<PlayerType> playerTypeList) {
        Map<Player, List<PlayerType>> playerPlayerTypeListMap = new HashMap<Player, List<PlayerType>>();
        for(PlayerType playerType : playerTypeList)
        {
            if(!playerPlayerTypeListMap.containsKey(playerType.getPlayer()))
            {
                playerPlayerTypeListMap.put(playerType.getPlayer(), new ArrayList<PlayerType>());
            }
            playerPlayerTypeListMap.get(playerType.getPlayer()).add(playerType);
        }
        return playerPlayerTypeListMap;
    }
}
