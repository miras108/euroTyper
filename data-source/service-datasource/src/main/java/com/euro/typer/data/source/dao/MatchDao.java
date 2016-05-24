package com.euro.typer.data.source.dao;

import com.euro.typer.data.source.criteria.DateHelper;
import com.euro.typer.data.source.criteria.MatchCriteria;
import com.euro.typer.data.source.entity.Country;
import com.euro.typer.data.source.entity.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class MatchDao {
    private SessionFactory sessionFactory;
    private MatchCriteria matchCriteria;

    public MatchDao(MatchCriteria matchCriteria, SessionFactory sessionFactory) {
        this.matchCriteria = matchCriteria;
        this.sessionFactory = sessionFactory;
    }

    public Match getMatchByCriteria(Country country1, Country country2, String time) throws ParseException {
        Date matchDate = DateHelper.getDateFromStringWithoutTime(time);

        Date fromDate = DateHelper.getDateWithoutTime(matchDate);
        Date toDate = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate(matchDate));

        Match match = matchCriteria.getByCountriesAndDate(country1, country2, fromDate, toDate);
        if(match == null)
        {
            match = matchCriteria.getByCountriesAndDate(country2, country1, fromDate, toDate);
        }
        return match;
    }


    public Match getMatchByDateTime(String dateTime) throws ParseException {

        Date time = DateHelper.getDateWithTime(dateTime);
        return matchCriteria.getByDateWithTime(time);
    }


    public List<Match> getMatchByDate(Date matchDate) throws ParseException {

        Date fromDate = DateHelper.getDateWithoutTime(matchDate);
        Date toDate = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate(matchDate));

        return matchCriteria.getByDate(fromDate, toDate);
    }


    public List<Match> getMatchForDayByDateWithTime(String time) throws ParseException {

        Date matchDate = DateHelper.getDateWithTime(time);
        Date fromDate = DateHelper.getDateWithoutTime(matchDate);
        Date toDate = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate(matchDate));

        return matchCriteria.getByDate(fromDate, toDate);
    }

    @Transactional
    public void saveMatch(Match match)
    {
        Session session = sessionFactory.openSession();
        session.save(match);
        session.flush();
    }

    @Transactional
    public void updateeMatch(Match match)
    {
        Session session = sessionFactory.openSession();
        session.update(match);
        session.flush();
    }
}
