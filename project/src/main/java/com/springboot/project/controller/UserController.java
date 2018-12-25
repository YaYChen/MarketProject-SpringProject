package com.springboot.project.controller;

import com.alibaba.fastjson.JSON;
import com.springboot.project.entity.User;
import com.springboot.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin//跨域注解
public class UserController {

    private UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper){
        this.userMapper=userMapper;
    }

    @RequestMapping("/users")
    public String users(){
        List<User> users = userMapper.selectAll();
        String usersJson = JSON.toJSONString(users);
        return usersJson;
    }
}
