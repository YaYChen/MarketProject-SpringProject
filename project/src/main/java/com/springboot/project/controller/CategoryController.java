package com.springboot.project.controller;

import com.springboot.project.entity.Category;
import com.springboot.project.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//跨域注解
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryMapper mapper;

    @Autowired
    public CategoryController(CategoryMapper mapper){this.mapper=mapper;}

    @GetMapping(value = "/getCategories")
    @ResponseBody
    public ResponseEntity<List<Category>> getCategories(){
        logger.info("Get all categories...");
        return ResponseEntity.ok(mapper.getAll());
    }

    @PostMapping(value = "/update-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> update(@RequestBody Category category){
        logger.info("Update category(id: "+ category.getId() +")...");
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            mapper.update(category);
            logger.info("Update category(id: "+ category.getId() +") success!");
            map.put("message", "");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            logger.error("Update category(id: "+ category.getId() +") cause abnormal: " + e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/insert-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> insert(@RequestBody Category category){
        logger.info("Insert category...");
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            mapper.insert(category);
            logger.info("Insert category success!");
            map.put("message", "");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            map.put("message", e.getMessage());
            logger.error("Insert category cause abnormal: " + e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/delete-category")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("id") int id){
        logger.info("Update category(id: "+ id +")...");
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            mapper.delete(id);
            logger.info("Delete category success!");
            map.put("message", "");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            logger.info("Delete category cause abnormal: " + e.getMessage());
            map.put("message", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_MODIFIED);
        }
    }
}
