package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.User;
import com.springboot.project.service.UserInfoService;
import com.springboot.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin//跨域注解
public class UserController {

    private UserService userService;
    private UserInfoService historyService;

    private JwtHelper jwtHelper;

    @Autowired
    public UserController(UserService userService, UserInfoService historyService, JwtHelper jwtHelper){
        this.userService = userService;
        this.historyService = historyService;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping(value = "/getallusers")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/adduser")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> signUp(@RequestBody User newUser) {
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            newUser.setGenTime(new Date());
            this.userService.registerUser(newUser);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> signIn(@RequestBody User loginUser) {
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            Map<String, Object> claims = new HashMap<String, Object>();
            User user = userService.getUserForIdentify(loginUser.getLoginName());
            if ( user!=null && user.getPassword().equals(loginUser.getPassword())) {
                map.put("message", "Success!");
                claims.put("userId", user.getId());
                map.put("token", jwtHelper.generateToken(claims));
                map.put("userName", user.getUserName());
                map.put("userId", user.getId());
                historyService.insertLoginHistory(user.getId());
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {
                map.put("message", "登录帐号或者登录密码错误");
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
        }

    }
}
