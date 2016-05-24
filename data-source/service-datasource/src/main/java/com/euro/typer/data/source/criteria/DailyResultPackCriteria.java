package com.euro.typer.data.source.criteria;

import com.euro.typer.data.source.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class DailyResultPackCriteria {

    private SessionFactory sessionFactory;

    public DailyResultPackCriteria(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    public DailyResultPack getByDate(Date fromDate, Date toDate)
    {
        Session session = openSession();
        Criteria criteria = session.createCriteria(DailyResultPack.class)
                .add(Restrictions.ge("date", fromDate))
                .add(Restrictions.le("date", toDate));

        return (DailyResultPack) criteria.uniqueResult();
    }


    public Session openSession() {
        return sessionFactory.openSession();
    }
}
