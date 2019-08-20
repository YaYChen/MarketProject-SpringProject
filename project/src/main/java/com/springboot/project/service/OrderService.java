package com.springboot.project.service;

import com.springboot.project.entity.Order;
import com.springboot.project.entity.OrderItem;
import com.springboot.project.mapper.OrderItemMapper;
import com.springboot.project.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper,OrderItemMapper orderItemMapper){
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Cacheable(value = "orderCache",key = "#serial",unless = "#result == null")
    public Order getOrderBySerial(String serial,int usesrId){
        return orderMapper.getOne(serial,usesrId);
    }

    @Cacheable(value = "orderCache",key = "#root.targetClass.concat(#id)",unless = "#result == null")
    public Order getOrderById(int id){
        return orderMapper.getOrderById(id);
    }

    @Cacheable(value = "orderCache",key = "#userId",unless = "#result == null")
    public List<Order> getAllOrder(int userId){
        return orderMapper.getAllOrder(userId);
    }

    public List<Order> searchOrderByDate(Date startDate, Date endDate, int userId){
        System.out.println(startDate.toString());
        System.out.println(endDate.toString());
        return orderMapper.searchByDate(startDate, endDate, userId);
    }

    @CacheEvict(value = "orderCache",key = "#userId")
    public void createOrder(Order order, int userId) throws Exception{
        order.setCreateTime(new Date());
        orderMapper.insert(order);
        Order newOrder = orderMapper.getOne(order.getSerial(),userId);
        for (OrderItem orderItem :order.getOrderItems()) {
            orderItem.setOrderID(newOrder.getId());
            orderItemMapper.insert(orderItem);
        }
    }

    @CachePut(value = "orderCache",key = "#root.targetClass.concat(#order.id)")
    public Order updateOrder(Order order,int userId) throws Exception{
        Order oldOrder = orderMapper.getOne(order.getSerial(),userId);
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
        return order;
    }

    @CacheEvict(value = "orderCache",key = "#serial")
    public void deleteOrder(String serial, int userId) throws Exception{
        Order order = orderMapper.getOne(serial,userId);
        for(OrderItem item : order.getOrderItems()){
            orderItemMapper.delete(item.getId());
        }
        orderMapper.delete(order.getId());
    }
}
