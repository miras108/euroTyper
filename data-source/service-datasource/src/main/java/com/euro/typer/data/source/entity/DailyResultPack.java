package com.euro.typer.data.source.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * Created by miras108 on 2016-05-21.
 */
@Entity
public class DailyResultPack {

    @Id
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private Integer id;

    Date date;

    @OneToMany
    List<DailyResult> dailyResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<DailyResult> getDailyResults() {
        return dailyResults;
    }

    public void setDailyResults(List<DailyResult> dailyResults) {
        this.dailyResults = dailyResults;
    }
}
