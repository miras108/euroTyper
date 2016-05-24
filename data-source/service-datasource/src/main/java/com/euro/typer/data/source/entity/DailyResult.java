package com.euro.typer.data.source.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by miras108 on 2016-05-21.
 */
@Entity
public class DailyResult {

    @Id
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private Integer id;

    @ManyToOne
    private Player player;

    @ManyToMany
    private List<PlayerType> playerTypes;

    private Double points;

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

    public List<PlayerType> getPlayerTypes() {
        return playerTypes;
    }

    public void setPlayerTypes(List<PlayerType> playerTypes) {
        this.playerTypes = playerTypes;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
