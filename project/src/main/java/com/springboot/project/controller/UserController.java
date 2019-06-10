package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.User;
import com.springboot.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class UserController {

    private UserService userService;

    private JwtHelper jwtHelper;

    @Autowired
    public UserController(UserService userService,JwtHelper jwtHelper){
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping(value = "/getUsers")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(String loginName,String password) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String, Object> claims = new HashMap<String, Object>();
        User user = userService.getUserForIdentify(loginName);
        if (user.getPassword().equals(password)) {
            claims.put("userId", user.getId());
            map.put("token", jwtHelper.generateToken(claims));
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        } else {
            map.put("error", "登录帐号或者登录密码错误");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
