package com.euro.typer.data.source.dao;

import com.euro.typer.data.source.criteria.UserCriteria;
import com.euro.typer.data.source.entity.Match;
import com.euro.typer.data.source.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserDao {
    private SessionFactory sessionFactory;
    private UserCriteria userCriteria;

    public UserDao(UserCriteria userCriteria, SessionFactory sessionFactory) {
        this.userCriteria = userCriteria;
        this.sessionFactory = sessionFactory;
    }

    public Player getUserById(Integer userId) {
        return userCriteria.getByUserId(userId);
    }

    public Player getUserByUsername(String username) {
        return userCriteria.getByUsername(username);
    }

    public List<Player> getAllUsers()
    {
        return sessionFactory.openSession().createCriteria(Player.class).list();
    }

    @Transactional
    public void saveUser(Player player)
    {
        Session session = sessionFactory.openSession();
        session.save(player);
        session.flush();
    }
}
