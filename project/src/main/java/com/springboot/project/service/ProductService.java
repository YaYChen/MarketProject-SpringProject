package com.springboot.project.service;

import com.springboot.project.entity.Product;
import com.springboot.project.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductService {

    private ProductMapper productMapper;

    @Autowired
    public ProductService(ProductMapper productMapper){this.productMapper = productMapper;}

    public Product getProductByCode(String code){
        return productMapper.getProductByCode(code);
    }

    public List<Product> getProductByCategory(int categoryID){
        return productMapper.getProductsByCategory(categoryID);
    }

    public void updateProduct(Product product) throws Exception{
        productMapper.update(product);
    }

    public void insertProduct(Product product) throws Exception{
        productMapper.insert(product);
    }

    public void deleteProduct(int id) throws Exception{
        productMapper.delete(id);
    }
}
