package com.euro.typer.data.source.dao;

import com.euro.typer.data.source.criteria.PlayerTypeCriteria;
import com.euro.typer.data.source.entity.DailyResult;
import com.euro.typer.data.source.entity.Match;
import com.euro.typer.data.source.entity.Player;
import com.euro.typer.data.source.entity.PlayerType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class DailyResultDao {
    private SessionFactory sessionFactory;

    public DailyResultDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<DailyResult> getAll() {
        return sessionFactory.openSession().createCriteria(DailyResult.class).list();
    }

    @Transactional
    public void saveDailyResults(List<DailyResult> dailyResults)
    {
        Session session = sessionFactory.openSession();
        for(DailyResult dailyResult : dailyResults)
        {
            session.save(dailyResult);
        }
        session.flush();
    }
}
