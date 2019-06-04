package com.springboot.project.service;

import com.springboot.project.entity.Category;
import com.springboot.project.mapper.CategoryMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }

    @Cacheable(value = "categoryCache",key = "#id")
    public Category getOneCategory(int id){
        return categoryMapper.getCategory(id);
    }

    @Cacheable(value = "categoryCache",key = "allCategory")
    public List<Category> getAllCategories(){
        return categoryMapper.getAll();
    }

    @CachePut(value = "categoryCache",key = "#result.id")
    public Category updateCategory(Category category) throws Exception{
        categoryMapper.update(category);
        return category;
    }

    public Category insertCategory(Category category) throws Exception{
        return this.getOneCategory(categoryMapper.insert(category));
    }

    @CacheEvict(value = "categoryCache",key = "#id")
    public void deleteCategory(int id) throws Exception{
        categoryMapper.delete(id);
    }
}
