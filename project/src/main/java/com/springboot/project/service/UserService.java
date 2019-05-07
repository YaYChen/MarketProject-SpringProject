package com.springboot.project.service;

import com.springboot.project.entity.User;
import com.springboot.project.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@MapperScan("com.springboot.project.mapper")
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
}
