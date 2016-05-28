package org.arpit.java2blog.score;

import org.arpit.java2blog.controller.ScoreService;
import org.springframework.scheduling.TaskScheduler;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by miras108 on 2016-05-28.
 */
public class DailyScoreTask implements Runnable{

    private DayScoreCreator dayScoreCreator;

    public DailyScoreTask(DayScoreCreator dayScoreCreator) {
        this.dayScoreCreator = dayScoreCreator;
    }

    @Override
    public void run() {
        try {
            System.out.println("execute task");
            dayScoreCreator.createDayScore(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
