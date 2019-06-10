package com.springboot.project.controller;

import com.springboot.project.authenticate.JwtHelper;
import com.springboot.project.entity.Category;
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

    @GetMapping(value = "/getCategories")
    @ResponseBody
    public ResponseEntity<List<Category>> getCategories(){
        Map<String, Object> claim = jwtHelper.validateTokenAndGetClaims(request);
        return ResponseEntity.ok(categoryService.getAllCategories(Integer.getInteger(claim.get("userId").toString())));
    }

    @PostMapping(value = "/update-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> update(@RequestBody Category category){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            categoryService.updateCategory(category);
            map.put("message", "");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/insert-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> insert(@RequestBody Category category){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            categoryService.insertCategory(category);
            map.put("message", "");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/delete-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("id") int id){
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            categoryService.deleteCategory(id);
            map.put("message", "");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }
}
