package com.euro.typer.data.source.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Player {

    @Id
    private Integer id;

    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
