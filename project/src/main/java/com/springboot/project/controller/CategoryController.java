package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.Category;
import com.springboot.project.entity.User;
import com.springboot.project.service.CategoryService;
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
public class CategoryController {

    @Autowired
    private HttpServletRequest request;

    private CategoryService categoryService;

    private JwtHelper jwtHelper;

    @Autowired
    public CategoryController(CategoryService categoryService,JwtHelper jwtHelper){
        this.categoryService = categoryService;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping(value = "/p/getCategories")
    @ResponseBody
    public ResponseEntity<List<Category>> getCategories(){
        int userId = (int)jwtHelper.validateTokenAndGetClaims(request).get("userId");
        return ResponseEntity.ok(categoryService.getAllCategories(userId));
    }

    @PostMapping(value = "/p/update-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> update(@RequestBody Category category){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            categoryService.updateCategory(category);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/p/insert-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> insert(@RequestBody Category category){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            int userId = (int)jwtHelper.validateTokenAndGetClaims(request).get("userId");
            User user = new User();
            user.setId(userId);
            category.setUserId(user);
            categoryService.insertCategory(category);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/p/delete-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("id") int id){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            int userId = (int)jwtHelper.validateTokenAndGetClaims(request).get("userId");
            categoryService.deleteCategory(id,userId);
            map.put("message", "Success!");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }
}
