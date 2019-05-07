package com.springboot.project.service;

import com.springboot.project.entity.Category;
import com.springboot.project.mapper.CategoryMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@MapperScan("com.springboot.project.mapper")
@Transactional
public class CategoryService {

    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }

    public List<Category> getAllCategories(){
        return categoryMapper.getAll();
    }

    public void updateCategory(Category category) throws Exception{
        categoryMapper.update(category);
    }

    public void insertCategory(Category category) throws Exception{
        categoryMapper.insert(category);
    }

    public void deleteCategory(int id) throws Exception{
        categoryMapper.delete(id);
    }
}
