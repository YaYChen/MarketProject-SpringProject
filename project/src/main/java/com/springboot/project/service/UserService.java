package com.springboot.project.service;

import com.springboot.project.entity.User;
import com.springboot.project.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public List<User> getAllUsers(){
        List<User> list = this.userMapper.selectAll();
        for(User user:list){
            user.setPassword("*********");
        }
        return  list;
    }

    public User getUserForIdentify(String loginName){
        return userMapper.getUserByLoginName(loginName);
    }

    public void registeUser(User newUser){
        newUser.setGenTime(new Date());
        newUser.setCount(0);
        this.userMapper.insert(newUser);
    }

    public void updateUserLoginHistory(int userId, int count){
        this.userMapper.updateHistoryInfo(userId,count,new Date());
    }
}
