package com.springboot.project.service;

import com.springboot.project.entity.LoginHistory;
import com.springboot.project.entity.User;
import com.springboot.project.mapper.LoginHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@Transactional
public class LoginHistoryService {

    private LoginHistoryMapper mapper;

    @Autowired
    public LoginHistoryService(LoginHistoryMapper mapper){
        this.mapper = mapper;
    }

    @Cacheable(value = "login-history",key = "#userId")
    public List<LoginHistory> getLoginHistoryList(int userId){
        return this.mapper.getAllLoginHistoryByUserId(userId);
    }

    @CacheEvict(value = "login-history",key = "#userId")
    public void insertLoginHistory(int userId){
        LoginHistory history = new LoginHistory();
        User user = new User();
        user.setId(userId);
        history.setLoginUser(user);
        history.setLoginDate(new Date());
        this.mapper.insertLoginHistory(history);
    }
}
