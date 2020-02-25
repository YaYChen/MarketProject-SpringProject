package com.springboot.project.service;

import com.springboot.project.entity.Order;
import com.springboot.project.entity.OrderItem;
import com.springboot.project.entity.Page;
import com.springboot.project.mapper.OrderItemMapper;
import com.springboot.project.mapper.OrderMapper;
import com.springboot.project.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private RedisUtil redisUtil;

    public Order getOrderBySerial(String serial,int userId){
        Map<Object,Object> cacheMap = this.redisUtil.hmget("orderCache" + userId);
        Order order;
        if(cacheMap == null || cacheMap.isEmpty()){
            order = this.orderMapper.getOne(serial,userId);
            this.redisUtil.hset("orderCache" + userId, String.valueOf(order.getId()), order);
        }else{
            List<Object> resultList =
                    cacheMap.values().stream()
                            .filter(i->((Order)i).getSerial().equals(serial))
                            .collect(Collectors.toList());
            if(resultList.isEmpty()){
                order = this.orderMapper.getOne(serial,userId);
                this.redisUtil.hset("orderCache" + userId, String.valueOf(order.getId()), order);
            }else{
                order = (Order) resultList.get(0);
            }
        }
        return order;
    }

    public Order getOrderById(int id){
        return orderMapper.getOrderById(id);
    }

    public List<Order> getAllOrder(int userId){
        Object cacheObject = this.redisUtil.hget("orderCache" + userId, "allOrders");
        List<Order> orders = new ArrayList<>();
        if(cacheObject != null){
            orders = (List<Order>) cacheObject;
        }else{
            orders = this.orderMapper.getAllOrder(userId);
            this.redisUtil.hset("orderCache" + userId, "allOrders", orders);
        }
        return orders;
    }

    public Page getAllOrdersByPage(int userId, int pageSize, int pageNO){
        Object cacheObject = this.redisUtil.hget("orderCache" + userId, "orders" + pageSize);
        List<Page> pages = new ArrayList<>();
        if(cacheObject != null){
            pages = (List<Page>) cacheObject;
        }else{
            List<Order> orders = this.getAllOrder(userId);
            int length = orders.size();
            if(length > 0) {
                pages = new ArrayList<>();
                int totalCount = (length + pageSize - 1) / pageSize;
                for (int i = 0; i < totalCount; i++) {
                    Page page = new Page();
                    page.setPageNo(String.valueOf(i));
                    page.setPageSize(String.valueOf(pageSize));
                    page.setTotal(String.valueOf(totalCount));
                    int fromIndex = i * pageSize;
                    int toIndex = (i + 1) * pageSize < length ? (i + 1) * pageSize : length;
                    page.setRows(orders.subList(fromIndex, toIndex));
                    pages.add(page);
                }
                this.redisUtil.hset("orderCache" + userId, "orders" + pageSize, pages);
            }
        }
        return (pageNO < 0 || pageNO > pages.size()) ? null : pages.get(pageNO);
    }

    public List<Order> searchOrderByDate(Date startDate, Date endDate, int userId){
        List<Order> orders = this.getAllOrder(userId);
        return orders.stream()
                .filter(i -> i.getCreateTime().before(startDate) && i.getCreateTime().after(endDate))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createOrder(Order order, int userId) {
        order.setCreateTime(new Date());
        this.orderMapper.insert(order);
        Order newOrder = this.orderMapper.getOne(order.getSerial(),userId);
        for (OrderItem orderItem :order.getOrderItems()) {
            orderItem.setOrderID(newOrder.getId());
            this.orderItemMapper.insert(orderItem);
        }
    }

    @Transactional
    public Order updateOrder(Order order,int userId) {
        Order oldOrder = this.orderMapper.getOne(order.getSerial(),userId);
        List<Long> idList = new ArrayList<Long>();
        for(OrderItem orderItem:oldOrder.getOrderItems()){
            idList.add(orderItem.getId());
        }
        for(OrderItem item:order.getOrderItems()){
            if(idList.contains(item.getId())){
                this.orderItemMapper.update(item);
            }else{
                this.orderItemMapper.delete(item.getId());
            }
        }
        this.orderMapper.update(order);
        return order;
    }

    @Transactional
    public void deleteOrder(String serial, int userId, int id) {
        Order order = this.orderMapper.getOne(serial,userId);
        for(OrderItem item : order.getOrderItems()){
            this.orderItemMapper.delete(item.getId());
        }
        this.orderMapper.delete(order.getId());
    }
}
