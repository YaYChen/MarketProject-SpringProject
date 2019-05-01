package com.springboot.project.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin//跨域注解
public class HomeController {

    @RequestMapping("/home")
    public String home(){
        return "home";
    }
}
