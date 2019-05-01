package com.springboot.project.service;

import com.springboot.project.entity.User;
import com.springboot.project.mapper.UserMapper;

import java.util.List;

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
}
