package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.Order;
import com.springboot.project.service.OrderService;
import com.springboot.project.wxpay.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SettleController {

    @Autowired
    private WxService wxService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private OrderService orderService;

    @PostMapping("p/wxpay")
    public ResponseEntity<Map<String,Object>> wxPayNotify(@RequestBody Order order) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            int userId =
                    (int)jwtHelper.validateTokenAndGetClaims(request)
                            .get("userId");
            result = wxService.payBack(order, userId);
            result.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(result, HttpStatus.NOT_MODIFIED);
        }
    }
}
