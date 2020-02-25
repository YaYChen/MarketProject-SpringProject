package com.springboot.project.service;

import com.springboot.project.entity.Product;
import com.springboot.project.entity.SalesVolume;
import com.springboot.project.mapper.ProductMapper;
import com.springboot.project.mapper.SalesVolumeMapper;
import com.springboot.project.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SalesVolumeMapper salesVolumeMapper;

    @Autowired
    private RedisUtil redisUtil;

    public Product getProductById(int userId, int id){
        Object cacheObject = this.redisUtil.hget("productCache" + userId, String.valueOf(id));
        Product product;
        if(cacheObject != null){
            product = (Product) this.redisUtil.hget("productCache" + userId, String.valueOf(id));
        }else{
            product = this.productMapper.getProductByID(id);
            this.redisUtil.hset("productCache" + userId, String.valueOf(id), product);
        }
        return product;
    }

    public Product getProductByCode(String code,int userId){
        Map<Object,Object> cacheMap = this.redisUtil.hmget("productCache" + userId);
        Product product;
        if(cacheMap == null || cacheMap.isEmpty()){
            product = this.productMapper.getProductByCode(code,userId);
            this.redisUtil.hset("productCache" + userId, String.valueOf(product.getId()), product);
        }else{
            List<Object> products = cacheMap.values().stream()
                    .filter(i -> ((Product)i).getCode().equals(code))
                    .collect(Collectors.toList());
            if(products.size() > 0){
                return (Product) products.get(0);
            }else{
                product = this.productMapper.getProductByCode(code,userId);
                this.redisUtil.hset("productCache" + userId, String.valueOf(product.getId()), product);
            }
        }
        return product;
    }

    public List<Product> getProductByCategory(int categoryID,int userId){
        Object cacheObject = this.redisUtil
                .hget("productCache" + userId, "productListByCategory" + categoryID);
        List<Product> products = new ArrayList<>();
        if(cacheObject != null){
            products = (List<Product>) this.redisUtil
                            .hget("productCache" + userId, "productListByCategory" + categoryID);
        }else{
            products = this.productMapper.getProductsByCategory(categoryID, userId);
            this.redisUtil.hset("productCache" + userId, "productListByCategory" + categoryID, products);
        }
        return products;
    }

    public Product updateProduct(Product product) {
        this.productMapper.update(product);
        this.redisUtil.hset("productCache" + product.getUserId(), String.valueOf(product.getId()), product);
        return product;
    }

    public void insertProduct(Product product) {
        this.redisUtil.hset("productCache" + product.getUserId(), String.valueOf(product.getId()), product);
        this.productMapper.insert(product);
    }

    public void deleteProduct(int userId, int id) {
        this.redisUtil.hdel("productCache" + userId, String.valueOf(id));
        this.productMapper.delete(id);
    }

    public List<SalesVolume> getWholeSalesVolume(int userId){
        Object cacheObject = this.redisUtil.hget("productCache" + userId, "SalesVolume");
        List<SalesVolume> volumes;
        if(cacheObject != null){
            volumes = (List<SalesVolume>) this.redisUtil.hget("productCache" + userId, "SalesVolume");
        }else{
            volumes = this.salesVolumeMapper.getWholeSalesVolume(userId);
            this.redisUtil.hset("productCache" + userId, "SalesVolume", volumes);
        }
        return volumes;
    }
}
