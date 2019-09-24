package com.springboot.project.wxpay;

import com.springboot.project.entity.Order;

import java.util.Map;

public interface WxService {
    Map<String,Object> payBack(Order order, int userId) throws Exception;
    Map doUnifiedOrder(int userId) throws Exception;
}
