package com.euro.typer.data.source.criteria;

import com.euro.typer.data.source.entity.Match;
import com.euro.typer.data.source.entity.Player;
import com.euro.typer.data.source.entity.PlayerType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PlayerTypeCriteria {

    private SessionFactory sessionFactory;

    public PlayerTypeCriteria(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    public PlayerType getByPlayerAndMatch(Player player, Match match) {
        Session session = openSession();
        Criteria criteria = session.createCriteria(PlayerType.class)
                .add(Restrictions.eq("player", player))
                .add(Restrictions.eq("match", match));

        return (PlayerType) criteria.uniqueResult();
    }

    public List<PlayerType> getByMatch(Match match) {
        Session session = openSession();
        Criteria criteria = session.createCriteria(PlayerType.class)
                .add(Restrictions.eq("match", match));

        return criteria.list();
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }
}
