package com.springboot.project.service;

import com.springboot.project.entity.Product;
import com.springboot.project.entity.SalesVolume;
import com.springboot.project.mapper.ProductMapper;
import com.springboot.project.mapper.SalesVolumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private ProductMapper productMapper;

    private SalesVolumeMapper salesVolumeMapper;

    @Autowired
    public ProductService(ProductMapper productMapper,SalesVolumeMapper salesVolumeMapper){
        this.productMapper = productMapper;
        this.salesVolumeMapper = salesVolumeMapper;
    }

    @Cacheable(value = "productCache",key = "#id")
    public Product getProductById(int id,int userId){
        return productMapper.getProductByID(id,userId);
    }

    @Cacheable(value = "productCache",key = "#code")
    public Product getProductByCode(String code,int userId){
        return productMapper.getProductByCode(code,userId);
    }

    @Cacheable(value = "productCache",key = "#categoryID")
    public List<Product> getProductByCategory(int categoryID,int userId){
        return productMapper.getProductsByCategory(categoryID,userId);
    }

    @CachePut(value = "productCache",key = "#result.id")
    public Product updateProduct(Product product) throws Exception{
        productMapper.update(product);
        return product;
    }

    public Product insertProduct(Product product) throws Exception{
        int id = productMapper.insert(product);
        return this.getProductById(id,product.getCreateUser().getId());
    }

    @CacheEvict(value = "productCache",key = "#id")
    public void deleteProduct(int id) throws Exception{
        productMapper.delete(id);
    }

    @Cacheable(value = "productCache",key = "#root.methodName")
    public List<SalesVolume> getWholeSalesVolume(){
        return salesVolumeMapper.getWholeSalesVolume();
    }
}
