package com.springboot.project.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Category implements Serializable {

    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
