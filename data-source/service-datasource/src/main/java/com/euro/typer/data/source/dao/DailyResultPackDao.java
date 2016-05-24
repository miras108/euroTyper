package com.euro.typer.data.source.dao;

import com.euro.typer.data.source.criteria.DailyResultPackCriteria;
import com.euro.typer.data.source.criteria.DateHelper;
import com.euro.typer.data.source.criteria.MatchCriteria;
import com.euro.typer.data.source.entity.Country;
import com.euro.typer.data.source.entity.DailyResult;
import com.euro.typer.data.source.entity.DailyResultPack;
import com.euro.typer.data.source.entity.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class DailyResultPackDao {
    private SessionFactory sessionFactory;
    private DailyResultPackCriteria dailyResultPackCriteria;

    public DailyResultPackDao(DailyResultPackCriteria dailyResultPackCriteria, SessionFactory sessionFactory) {
        this.dailyResultPackCriteria = dailyResultPackCriteria;
        this.sessionFactory = sessionFactory;
    }


    public DailyResultPack getByDate(Date date) throws ParseException {

        Date fromDate = DateHelper.getDateWithoutTime(date);
        Date toDate = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate(date));

        return dailyResultPackCriteria.getByDate(fromDate, toDate);
    }

    @Transactional
    public void save(DailyResultPack dailyResultPack)
    {
        Session session = sessionFactory.openSession();
        session.save(dailyResultPack);
        session.flush();
    }
}
