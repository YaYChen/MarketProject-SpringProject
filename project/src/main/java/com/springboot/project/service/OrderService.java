package com.springboot.project.service;

import com.springboot.project.entity.Order;
import com.springboot.project.entity.OrderItem;
import com.springboot.project.mapper.OrderItemMapper;
import com.springboot.project.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService {

    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper,OrderItemMapper orderItemMapper){
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public List<Order> searchOrderByUser(int userID){
        return orderMapper.searchByUser(userID);
    }

    public List<Order> searchOrderByDate(Date startDate,Date endDate){
        return orderMapper.searchByDate(startDate,endDate);
    }

    public void createOrder(Order order) throws Exception{
        order.setCreateTime(new Date());
        orderMapper.insert(order);
        Order newOrder = orderMapper.getOne(order.getSerial());
        for (OrderItem orderItem :order.getOrderItems()) {
            orderItem.setOrderID(newOrder.getId());
            orderItemMapper.insert(orderItem);
        }
    }

    public void updateOrder(Order order) throws Exception{
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
    }

    public void deleteOrder(String serial) throws Exception{
        Order order = orderMapper.getOne(serial);
        for(OrderItem item : order.getOrderItems()){
            orderItemMapper.delete(item.getId());
        }
        orderMapper.delete(order.getId());
    }
}
