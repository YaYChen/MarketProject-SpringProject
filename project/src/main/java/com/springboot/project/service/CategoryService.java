package com.springboot.project.service;

import com.springboot.project.entity.Category;
import com.springboot.project.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Cacheable(value = "categoryCache",key = "#id",unless = "#result == null")
    public Category getCategoryById(int id){
        return categoryMapper.getCategoryById(id);
    }

    @Cacheable(value = "categoryCache-all",key = "#userId",unless = "#result == null")
    public List<Category> getAllCategories(int userId){
        return categoryMapper.getAll(userId);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "categoryCache-all", key = "#result.getUser().getId()"),
            },
            put = {
                    @CachePut(value = "categoryCache",key = "#result.getId()")
            }
    )
    public Category updateCategory(Category category) throws Exception{
        categoryMapper.update(category);
        return category;
    }

    @CacheEvict(value = "categoryCache-all", key = "#category.getUser().getId()")
    public Category insertCategory(Category category) throws Exception{
        return this.getCategoryById(categoryMapper.insert(category));
    }


    @Caching(
            evict = {
                    @CacheEvict(value = "categoryCache",key = "#id"),
                    @CacheEvict(value = "categoryCache-all", key = "#userId"),
            }
    )
    public void deleteCategory(int id, int userId) throws Exception{
        categoryMapper.delete(id);
    }
}
