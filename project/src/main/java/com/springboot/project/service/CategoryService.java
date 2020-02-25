package com.springboot.project.service;

import com.springboot.project.entity.Category;
import com.springboot.project.mapper.CategoryMapper;
import com.springboot.project.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisUtil redisUtil;

    public Category getCategoryById(int userId, int id){
        Object cacheObject = this.redisUtil.hget("categoryCache" + userId, String.valueOf(id));
        Category category;
        if(cacheObject != null){
            category = (Category) cacheObject;
        }else{
            category = this.categoryMapper.getCategoryById(id);
            this.redisUtil.hset("categoryCache" + userId, String.valueOf(category.getId()), category);
        }
        return category;
    }

    public List<Category> getAllCategories(int userId){
        Map<Object,Object> cacheMap = this.redisUtil.hmget("categoryCache" + userId);
        List<Category> categories;
        if(cacheMap == null || cacheMap.isEmpty()){
            categories = this.categoryMapper.getAll(userId);
            Map<String,Object> newCacheMap = new HashMap<String,Object>();
            for (Category item : categories) {
                newCacheMap.put(String.valueOf(item.getId()), item);
            }
            this.redisUtil.hmset("categoryCache" + userId, newCacheMap);
        }else{
            categories = new ArrayList<>();
            for(Object item : cacheMap.values()){
                categories.add((Category) item);
            }
        }
        return categories;
    }

    public Category updateCategory(Category category) throws Exception{
        this.categoryMapper.update(category);
        this.redisUtil.hset("categoryCache" + category.getUserId(), String.valueOf(category.getId()), category);
        return category;
    }

    public void insertCategory(Category category) throws Exception{
        this.categoryMapper.insert(category);
        this.redisUtil.hset("categoryCache" + category.getUserId(), String.valueOf(category.getId()), category);
    }

    public void deleteCategory(int id, int userId) throws Exception{
        this.categoryMapper.delete(id);
        this.redisUtil.hdel("categoryCache" + userId, String.valueOf(id));
    }
}
