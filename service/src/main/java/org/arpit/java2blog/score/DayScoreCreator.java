package org.arpit.java2blog.score;

import com.euro.typer.data.source.dao.DailyResultDao;
import com.euro.typer.data.source.dao.DailyResultPackDao;
import com.euro.typer.data.source.dao.MatchDao;
import com.euro.typer.data.source.dao.PlayerTypeDao;
import com.euro.typer.data.source.entity.*;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

public class DayScoreCreator {
    private PlayerTypeDao playerTypeDao;
    private MatchDao matchDao;
    private DailyResultDao dailyResultDao;
    private DailyResultPackDao dailyResultPackDao;

    public String createDayScore(Date date) throws ParseException {

        if(dailyResultPackDao.getByDate(date) != null)
        {
            return "Daily results already generated";
        }

        List<Match> dayMatchList = matchDao.getMatchByDate(date);
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
        dailyResultPack.setDate(date);
        dailyResultPackDao.save(dailyResultPack);

        return "daily result generated";
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

    @Resource
    public void setPlayerTypeDao(PlayerTypeDao playerTypeDao) {
        this.playerTypeDao = playerTypeDao;
    }

    @Resource
    public void setMatchDao(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    @Resource
    public void setDailyResultDao(DailyResultDao dailyResultDao) {
        this.dailyResultDao = dailyResultDao;
    }

    @Resource
    public void setDailyResultPackDao(DailyResultPackDao dailyResultPackDao) {
        this.dailyResultPackDao = dailyResultPackDao;
    }
}
