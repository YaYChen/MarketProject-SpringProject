package com.springboot.project.controller;

import com.springboot.project.entity.Order;
import com.springboot.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin//跨域注解
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping(value = "/search-order-by-user")
    @ResponseBody
    public ResponseEntity<List<Order>> searchOrderByUser(@RequestParam(value = "user_id") int userID){
        return ResponseEntity.ok(orderService.searchOrderByUser(userID));
    }

    @PostMapping(value = "/search-order-by-date")
    @ResponseBody
    public ResponseEntity<List<Order>> searchOrderByDate(@RequestBody DateParam dateParam){
        return ResponseEntity.ok(orderService.searchOrderByDate(dateParam.start,dateParam.end));
    }

    @PostMapping(value = "/create-order")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> createOrder(@RequestBody Order order){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            orderService.createOrder(order);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/update-order")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateOrder(@RequestBody Order order){
        Map<String,Object> map = new HashMap<String, Object>();
        try{
            orderService.updateOrder(order);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/delete-order")
    @ResponseBody
    public ResponseEntity<Map<String,String>> deleteOrder(@RequestBody String serial){
        Map<String,String> map = new HashMap<String,String>();
        try{
           orderService.deleteOrder(serial);
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
