package com.springboot.project.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Supplier implements Serializable {
    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private String phone;
    @NotNull
    private String picture;
    @NotNull
    private User createUser;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }
}
