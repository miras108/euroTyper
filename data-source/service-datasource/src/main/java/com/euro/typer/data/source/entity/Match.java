package com.euro.typer.data.source.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by miras108 on 2016-05-21.
 */
@Entity
public class Match {

    @Id
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private Integer id;

    private Date time;

    @Enumerated(EnumType.STRING)
    private Country country1;

    @Enumerated(EnumType.STRING)
    private Country country2;

    private String score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Country getCountry1() {
        return country1;
    }

    public void setCountry1(Country country1) {
        this.country1 = country1;
    }

    public Country getCountry2() {
        return country2;
    }

    public void setCountry2(Country country2) {
        this.country2 = country2;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
