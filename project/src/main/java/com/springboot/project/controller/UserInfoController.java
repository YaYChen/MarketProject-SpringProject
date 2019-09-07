package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.LoginHistory;
import com.springboot.project.entity.User;
import com.springboot.project.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class UserInfoController {
    @Autowired
    private HttpServletRequest request;

    private UserInfoService service;
    private JwtHelper jwtHelper;

    @Autowired
    public UserInfoController(UserInfoService service, JwtHelper jwtHelper){
        this.service = service;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping(value = "/p/login-histories")
    @ResponseBody
    public ResponseEntity<List<LoginHistory>> loginHistories(){
        int userId =
                (int)jwtHelper.validateTokenAndGetClaims(request)
                        .get("userId");
        return ResponseEntity.ok(service.getLoginHistoryList(userId));
    }

    @GetMapping(value = "/p/user-detail")
    @ResponseBody
    public ResponseEntity<User> getUserDetail() {
        int userId =
                (int)jwtHelper.validateTokenAndGetClaims(request)
                        .get("userId");
        return ResponseEntity.ok(service.getUserDetail(userId));
    }

    @PostMapping(value = "/p/update-user-detail")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> updateUserDetail(@RequestBody User user){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            int userId =
                    (int)jwtHelper.validateTokenAndGetClaims(request)
                            .get("userId");
            this.service.updateUserDetail(userId, user);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }
}
