package com.euro.typer.data.source.criteria;

import com.euro.typer.data.source.entity.Player;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UserCriteria {

    private SessionFactory sessionFactory;

    public UserCriteria(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    public Player getByUserId(Integer userId) {
        Session session = openSession();
        Criteria criteria = session.createCriteria(Player.class)
                .add(Restrictions.eq("id", userId));

        return (Player) criteria.uniqueResult();
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
