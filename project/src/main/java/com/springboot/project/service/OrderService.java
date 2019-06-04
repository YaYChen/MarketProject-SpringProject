package com.springboot.project.service;

import com.springboot.project.entity.Order;
import com.springboot.project.entity.OrderItem;
import com.springboot.project.mapper.OrderItemMapper;
import com.springboot.project.mapper.OrderMapper;
import org.mybatis.spring.annotation.MapperScan;
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

    @Cacheable(value = "orderCache",key = "#serial")
    public Order getOrderBySerial(String serial){
        return orderMapper.getOne(serial);
    }

    @Cacheable(value = "orderCache",key = "#id")
    public Order getOrderById(int id){
        return orderMapper.getOrderById(id);
    }

    @Cacheable(value = "orderCache",key = "allOrder")
    public List<Order> getAllOrder(){
        return orderMapper.getAllOrder();
    }

    @Cacheable(value = "orderCache",key = "#userID")
    public List<Order> searchOrderByUser(int userID){
        return orderMapper.searchByUser(userID);
    }

    public List<Order> searchOrderByDate(Date startDate,Date endDate){
        return orderMapper.searchByDate(startDate,endDate);
    }

    public Order createOrder(Order order) throws Exception{
        order.setCreateTime(new Date());
        int id = orderMapper.insert(order);
        Order newOrder = orderMapper.getOne(order.getSerial());
        for (OrderItem orderItem :order.getOrderItems()) {
            orderItem.setOrderID(newOrder.getId());
            orderItemMapper.insert(orderItem);
        }
        return this.getOrderById(id);
    }

    @CachePut(value = "orderCache",key = "#result.id")
    public Order updateOrder(Order order) throws Exception{
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
        return order;
    }

    @CacheEvict(value = "orderCache",key = "#serial")
    public void deleteOrder(String serial) throws Exception{
        Order order = orderMapper.getOne(serial);
        for(OrderItem item : order.getOrderItems()){
            orderItemMapper.delete(item.getId());
        }
        orderMapper.delete(order.getId());
    }
}
