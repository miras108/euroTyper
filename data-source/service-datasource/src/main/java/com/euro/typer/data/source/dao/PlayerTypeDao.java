package com.euro.typer.data.source.dao;

import com.euro.typer.data.source.criteria.PlayerTypeCriteria;
import com.euro.typer.data.source.criteria.UserCriteria;
import com.euro.typer.data.source.entity.Match;
import com.euro.typer.data.source.entity.Player;
import com.euro.typer.data.source.entity.PlayerType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerTypeDao {
    private SessionFactory sessionFactory;
    private PlayerTypeCriteria playerTypeCriteria;

    public PlayerTypeDao(PlayerTypeCriteria playerTypeCriteria, SessionFactory sessionFactory) {
        this.playerTypeCriteria = playerTypeCriteria;
        this.sessionFactory = sessionFactory;
    }

    public PlayerType getByPlayerAndMatch(Player player, Match match) {
        return playerTypeCriteria.getByPlayerAndMatch(player, match);
    }

    public List<PlayerType> getByMatchList(List<Match> matchList) {
        List<PlayerType> playerTypeList = new ArrayList<PlayerType>();
        for(Match match : matchList)
        {
            playerTypeList.addAll(playerTypeCriteria.getByMatch(match));
        }
        return playerTypeList;
//        return matchList.stream()
//                .flatMap(match -> playerTypeCriteria.getByMatch(match).stream())
//                .collect(Collectors.toList());
    }

    public List<Player> getAllPlayers() {
        return sessionFactory.openSession().createCriteria(Player.class).list();
    }

    @Transactional
    public void savePlayerType(PlayerType playerType)
    {
        Session session = sessionFactory.openSession();
        session.save(playerType);
        session.flush();
    }
}
