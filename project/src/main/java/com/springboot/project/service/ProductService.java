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
import java.util.stream.Collectors;

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
    public Product getProductById(int id){
        return productMapper.getProductByID(id);
    }

    @Cacheable(value = "productCache",key = "#root.method.name.concat(#code)",unless = "#result == null")
    public Product getProductByCode(String code,int userId){
        return productMapper.getProductByCode(code,userId);
    }

    @Cacheable(value = "productCache",key = "#root.method.name.concat(#categoryID)",unless = "#result == null")
    public List<Product> getProductByCategory(int categoryID,int userId){
        return productMapper.getProductsByCategory(categoryID, userId);
    }

    @CachePut(value = "productCache",key = "#result.id")
    public Product updateProduct(Product product) throws Exception{
        productMapper.update(product);
        return product;
    }

    public void insertProduct(Product product) throws Exception{
        productMapper.insert(product);
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
