package org.arpit.java2blog.score;

import com.euro.typer.data.source.dao.DailyResultPackDao;
import com.euro.typer.data.source.entity.DailyResult;
import com.euro.typer.data.source.entity.DailyResultPack;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by miras108 on 2016-05-22.
 */
public class DayScoreCalculator {

    private DailyResultPackDao dailyResultPackDao;

    public List<Score> printDayScore(Date date) throws ParseException {

        DailyResultPack dailyResultPack = dailyResultPackDao.getByDate(date);
        List<Score> scoreList = new ArrayList<>();
        for(DailyResult dailyResult : dailyResultPack.getDailyResults())
        {
            Score score = new Score();
            score.setUsername(dailyResult.getPlayer().getUsername());
            score.setPoints(dailyResult.getPoints());

            scoreList.add(score);

            System.out.println(dailyResult.getPlayer().getUsername() + ": " + dailyResult.getPoints() + "pts");
        }

        return scoreList;
    }

    @Resource
    public void setDailyResultPackDao(DailyResultPackDao dailyResultPackDao) {
        this.dailyResultPackDao = dailyResultPackDao;
    }
}
