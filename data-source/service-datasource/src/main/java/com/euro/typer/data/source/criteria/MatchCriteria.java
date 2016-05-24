package com.euro.typer.data.source.criteria;

import com.euro.typer.data.source.entity.Country;
import com.euro.typer.data.source.entity.Match;
import com.euro.typer.data.source.entity.Player;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class MatchCriteria {

    private SessionFactory sessionFactory;

    public MatchCriteria(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    public Match getByCountriesAndDate(Country country1, Country country2, Date fromDate, Date toDate) {
        Session session = openSession();
        Criteria criteria = session.createCriteria(Match.class)
                .add(Restrictions.eq("country1", country1))
                .add(Restrictions.eq("country2", country2))
                .add(Restrictions.ge("time", fromDate))
                .add(Restrictions.le("time", toDate));

        return (Match) criteria.uniqueResult();
    }

    public List<Match> getByDate(Date fromDate, Date toDate)
    {
        Session session = openSession();
        Criteria criteria = session.createCriteria(Match.class)
                .add(Restrictions.ge("time", fromDate))
                .add(Restrictions.le("time", toDate));

        return criteria.list();
    }

    public Match getByDateWithTime(Date date)
    {
        Session session = openSession();
        Criteria criteria = session.createCriteria(Match.class)
                .add(Restrictions.eq("time", date));

        return (Match) criteria.uniqueResult();
    }

    public Player getByUsername(String username) {
        Session session = openSession();
        Criteria criteria = session.createCriteria(Player.class)
                .add(Restrictions.eq("username", username));

        return (Player) criteria.uniqueResult();
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }
}
