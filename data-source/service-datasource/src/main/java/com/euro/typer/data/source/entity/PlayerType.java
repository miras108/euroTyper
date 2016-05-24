package com.euro.typer.data.source.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by miras108 on 2016-05-21.
 */
@Entity
public class PlayerType {

    @Id
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private Integer id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Match match;

    private String typedScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getTypedScore() {
        return typedScore;
    }

    public void setTypedScore(String typedScore) {
        this.typedScore = typedScore;
    }
}
