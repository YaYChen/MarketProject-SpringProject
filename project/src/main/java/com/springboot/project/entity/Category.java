package com.springboot.project.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Category implements Serializable {

    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private User createUser;

    public User getUser() {
        return createUser;
    }

    public void setUserId(User createUser) {
        this.createUser = createUser;
    }

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

}
