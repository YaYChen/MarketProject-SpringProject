package com.springboot.project.service;

import com.springboot.project.entity.WxPay;
import com.springboot.project.mapper.WxPayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WxPayService {

    private WxPayMapper mapper;

    @Autowired
    public WxPayService(WxPayMapper mapper){
        this.mapper = mapper;
    }

    @Cacheable(value = "wxPayCache",key = "#userId")
    public WxPay getWxPayByUserID(int userId){
        return this.mapper.getWxPayByUserID(userId);
    }

    @CachePut(value = "wxPayCache",key = "#wxPay.getId()")
    public void updateWxPay(WxPay wxPay){
        this.mapper.update(wxPay);
    }

    public void insertWxPay(WxPay wxPay){
        this.mapper.insert(wxPay);
    }

    @CacheEvict(value = "wxPayCache",key = "#id")
    public void deleteWxPay(int id){
        this.mapper.delete(id);
    }
}
