package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.LoginHistory;
import com.springboot.project.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin//跨域注解
public class LoginHistoryController {
    @Autowired
    private HttpServletRequest request;

    private LoginHistoryService service;
    private JwtHelper jwtHelper;

    @Autowired
    public LoginHistoryController(LoginHistoryService service, JwtHelper jwtHelper){
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
}
