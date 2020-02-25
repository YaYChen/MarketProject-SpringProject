package com.springboot.project.service;

import com.springboot.project.entity.LoginHistory;
import com.springboot.project.entity.User;
import com.springboot.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserInfoService {

    @Autowired
    private UserMapper mapper;

    public List<LoginHistory> getLoginHistoryList(int userId){

        return this.mapper.getAllLoginHistoryByUserId(userId);
    }

    public void insertLoginHistory(int userId){
        LoginHistory history = new LoginHistory();
        history.setUserId(userId);
        history.setLoginDate(new Date());
        this.mapper.insertLoginHistory(history);
    }

    public User getUserDetail(int userId){
        return this.mapper.getUserDetail(userId);
    }

    public void updateUserDetail(int userId, User user){
        user.setId(userId);
        this.mapper.updateUserDetail(user);
    }

}
