package com.springboot.project.entity;

import java.io.Serializable;
import java.util.Date;

public class LoginHistory implements Serializable {
    private int id;
    private User loginUser;
    private Date loginDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
