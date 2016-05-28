package org.arpit.java2blog.score;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;

/**
 * Created by miras108 on 2016-05-28.
 */
public class DailyScoreExecutor {

    public static final String CRON_EXPRESSION = "0 0 1 * * ?";
    private DayScoreCreator dayScoreCreator;

    public DailyScoreExecutor(DayScoreCreator dayScoreCreator) {
        this.dayScoreCreator = dayScoreCreator;

        executeDayScore();
    }

    private void executeDayScore() {
        System.out.println("execute scheduler");
        CronTrigger cronTrigger = new CronTrigger(CRON_EXPRESSION);

        TaskScheduler taskScheduler = new DefaultManagedTaskScheduler();
        taskScheduler.schedule(new DailyScoreTask(dayScoreCreator), cronTrigger);
    }
}
