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


    @GetMapping(value = "/get-order-by-serial")
    @ResponseBody
    public ResponseEntity<Order> getOrderBySerial(@RequestParam(value = "serial") String serial){
        return ResponseEntity.ok(orderService.getOrderBySerial(serial));
    }

    @GetMapping(value = "/get-all-order")
    @ResponseBody
    public ResponseEntity<List<Order>> getAllOrder(){
        Map<String, Object> claim = jwtHelper.validateTokenAndGetClaims(request);
        return ResponseEntity.ok(orderService.getAllOrder(Integer.getInteger(claim.get("userId").toString())));
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
