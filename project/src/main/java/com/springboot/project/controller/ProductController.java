package com.springboot.project.controller;

import com.springboot.project.entity.Product;
import com.springboot.project.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class ProductController {

    private ProductMapper productMapper;

    @Autowired
    public ProductController(ProductMapper mapper){
        this.productMapper=mapper;
    }

    @GetMapping(value = "/products-ByCategory")
    public ResponseEntity<List<Product>> products(@RequestParam("category") int category){
        return ResponseEntity.ok(productMapper.getProductsByCategory(category));
    }

    @GetMapping(value = "/product-ByCode")
    public ResponseEntity<Product> product_ByCode(@RequestParam("code") String code){
        return ResponseEntity.ok(productMapper.getProductByCode(code));
    }

    @PostMapping(value = "/update-product")
    public ResponseEntity<Map<String,Object>> update(@RequestBody Product product){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            productMapper.update(product);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/insert-product")
    public ResponseEntity<Map<String,Object>> insert(@RequestBody Product product){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            productMapper.insert(product);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/delete-product")
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("id") int id){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            productMapper.delete(id);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }
}
