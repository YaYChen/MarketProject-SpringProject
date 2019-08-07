package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.Order;
import com.springboot.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@CrossOrigin//跨域注解
public class OrderController {

    @Autowired
    private HttpServletRequest request;

    private OrderService orderService;
    private JwtHelper jwtHelper;

    @Autowired
    public OrderController(OrderService orderService,JwtHelper jwtHelper){
        this.orderService = orderService;
        this.jwtHelper = jwtHelper;
    }


    @GetMapping(value = "/p/get-order-by-serial")
    @ResponseBody
    public ResponseEntity<Order> getOrderBySerial(@RequestParam(value = "serial") String serial){
        int userId =
                (int)jwtHelper.validateTokenAndGetClaims(request)
                        .get("userId");
        return ResponseEntity.ok(
                orderService.getOrderBySerial(serial,userId)
        );
    }

    @GetMapping(value = "/p/get-all-order")
    @ResponseBody
    public ResponseEntity<List<Order>> getAllOrder(){
        Map<String, Object> userInfo = jwtHelper.validateTokenAndGetClaims(request);
        return ResponseEntity.ok(
                orderService.getAllOrder((int)userInfo.get("userId"))
        );
    }

    @GetMapping(value = "/p/search-order-by-user")
    @ResponseBody
    public ResponseEntity<List<Order>> searchOrderByUser(){
        Map<String, Object> userInfo = jwtHelper.validateTokenAndGetClaims(request);
        return ResponseEntity.ok(
                orderService.searchOrderByUser((int)userInfo.get("userId"))
        );
    }

    @PostMapping(value = "/p/search-order-by-date")
    @ResponseBody
    public ResponseEntity<List<Order>> searchOrderByDate(@RequestBody DateParam dateParam){
        Map<String, Object> userInfo = jwtHelper.validateTokenAndGetClaims(request);
        return ResponseEntity.ok(
                orderService.searchOrderByDate(
                        dateParam.start,
                        dateParam.end,
                        (int)userInfo.get("userId"))
        );
    }

    @PostMapping(value = "/p/create-order")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> createOrder(@RequestBody Order order){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            int userId =
                    (int)jwtHelper.validateTokenAndGetClaims(request)
                            .get("userId");
            orderService.createOrder(order,userId);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/p/update-order")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateOrder(@RequestBody Order order){
        Map<String,Object> map = new HashMap<String, Object>();
        try{
            int userId =
                    (int)jwtHelper.validateTokenAndGetClaims(request)
                            .get("userId");
            orderService.updateOrder(order,userId);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/p/delete-order")
    @ResponseBody
    public ResponseEntity<Map<String,String>> deleteOrder(@RequestBody String serial){
        Map<String,String> map = new HashMap<String,String>();
        try{
            int userId =
                    (int)jwtHelper.validateTokenAndGetClaims(request)
                            .get("userId");
            orderService.deleteOrder(serial,userId);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    class DateParam{
        public Date start;
        public Date end;
    }
}
