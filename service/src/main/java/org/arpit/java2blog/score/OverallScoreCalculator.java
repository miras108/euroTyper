package org.arpit.java2blog.score;

import com.euro.typer.data.source.dao.DailyResultDao;
import com.euro.typer.data.source.entity.DailyResult;
import com.euro.typer.data.source.entity.Player;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miras108 on 2016-05-22.
 */
public class OverallScoreCalculator {

    private DailyResultDao dailyResultDao;
    
    public List<Score> printOverallScore()
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
        List<Score> scoreList = new ArrayList<>();
        for(Map.Entry<Player, Double> entry : playerPointsMap.entrySet())
        {
            Score score = new Score();
            score.setUsername(entry.getKey().getUsername());
            score.setPoints(entry.getValue());
            scoreList.add(score);
            System.out.println(entry.getKey().getUsername() + ": " + entry.getValue() + "pts");
        }
        
        return scoreList;
    }
    
    @Resource
    public void setDailyResultDao(DailyResultDao dailyResultDao) {
        this.dailyResultDao = dailyResultDao;
    }
}
