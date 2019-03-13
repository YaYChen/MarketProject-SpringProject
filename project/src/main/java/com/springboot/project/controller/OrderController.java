package com.springboot.project.controller;

import com.springboot.project.entity.Order;
import com.springboot.project.entity.OrderItem;
import com.springboot.project.mapper.OrderItemMapper;
import com.springboot.project.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin//跨域注解
public class OrderController {

    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;

    @Autowired
    public OrderController(OrderMapper orderMapper, OrderItemMapper orderItemMapper){
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @GetMapping(value = "/search-order-by-user")
    public ResponseEntity<List<Order>> searchOrderByUser(@RequestParam(value = "user_id") int userID){
        return ResponseEntity.ok(orderMapper.searchByUser(userID));
    }

    @PostMapping(value = "/search-order-by-date")
    public ResponseEntity<List<Order>> searchOrderByDate(@RequestBody DateParam dateParam){
        return ResponseEntity.ok(orderMapper.searchByDate(dateParam.start,dateParam.end));
    }

    @PostMapping(value = "/create-order")
    public ResponseEntity<Map<String,Object>> createOrder(@RequestBody Order order){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            order.setCreateTime(new Date());
            orderMapper.insert(order);
            Order newOrder = orderMapper.getOne(order.getSerial());
            for (OrderItem orderItem :order.getOrderItems()) {
                orderItem.setOrderID(newOrder.getId());
                orderItemMapper.insert(orderItem);
            }
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/update-order")
    public ResponseEntity<Map<String, Object>> updateOrder(@RequestBody Order order){
        Map<String,Object> map = new HashMap<String, Object>();
        try{
            Order oldOrder = orderMapper.getOne(order.getSerial());
            List<Long> idList = new ArrayList<Long>();
            for(OrderItem orderItem:oldOrder.getOrderItems()){
               idList.add(orderItem.getId());
            }
            for(OrderItem item:order.getOrderItems()){
                if(idList.contains(item.getId())){
                    orderItemMapper.update(item);
                }else{
                    orderItemMapper.delete(item.getId());
                }
            }
            orderMapper.update(order);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/delete-order")
    public ResponseEntity<Map<String,String>> deleteOrder(@RequestBody String serial){
        Map<String,String> map = new HashMap<String,String>();
        try{
            Order order = orderMapper.getOne(serial);
            for(OrderItem item : order.getOrderItems()){
                orderItemMapper.delete(item.getId());
            }
            orderMapper.delete(order.getId());
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
