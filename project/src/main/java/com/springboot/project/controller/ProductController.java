package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.Product;
import com.springboot.project.entity.SalesVolume;
import com.springboot.project.entity.User;
import com.springboot.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class ProductController {

    @Autowired
    private HttpServletRequest request;

    private ProductService productService;
    private JwtHelper jwtHelper;

    @Autowired
    public ProductController(ProductService productService,JwtHelper jwtHelper){
        this.productService = productService;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping(value = "/p/products-ByCategory")
    @ResponseBody
    public ResponseEntity<List<Product>> products(@RequestParam("category") int category){
        int userId =
                (int)jwtHelper.validateTokenAndGetClaims(request)
                        .get("userId");
        return ResponseEntity.ok(productService.getProductByCategory(category,userId));
    }

    @GetMapping(value = "/p/product-ByCode")
    @ResponseBody
    public ResponseEntity<Product> product_ByCode(@RequestParam("code") String code){
        int userId =
                (int)jwtHelper.validateTokenAndGetClaims(request)
                        .get("userId");
        return ResponseEntity.ok(productService.getProductByCode(code,userId));
    }

    @PostMapping(value = "/p/update-product")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> update(@RequestBody Product product){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            productService.updateProduct(product);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/p/insert-product")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> insert(@RequestBody Product product){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            int userId =
                    (int)jwtHelper.validateTokenAndGetClaims(request)
                            .get("userId");
            product.setUserId(userId);
            productService.insertProduct(product);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping(value = "/p/delete-product")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("id") int id){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            int userId =
                    (int)jwtHelper.validateTokenAndGetClaims(request)
                            .get("userId");
            productService.deleteProduct(userId, id);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping(value = "/p/getWholeSalesVolume")
    @ResponseBody
    public ResponseEntity<List<SalesVolume>> getWholeSalesVolume(){
        int userId =
                (int)jwtHelper.validateTokenAndGetClaims(request)
                        .get("userId");
        return ResponseEntity.ok(productService.getWholeSalesVolume(userId));
    }
}
