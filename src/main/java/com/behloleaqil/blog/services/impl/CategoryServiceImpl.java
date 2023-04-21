package com.behloleaqil.blog.services.impl;

import com.behloleaqil.blog.entities.Category;
import com.behloleaqil.blog.exceptions.ResourceNotFoundException;
import com.behloleaqil.blog.payloads.CategoryDTO;
import com.behloleaqil.blog.repositories.CategoryRepo;
import com.behloleaqil.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.categoryRepo.save(this.modelMapper.map(categoryDTO, Category.class));
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category foundCategory = this.categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", categoryId));
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        foundCategory.setCategoryTitle(category.getCategoryTitle());
        foundCategory.setCategoryDescription(category.getCategoryDescription());
        this.categoryRepo.save(foundCategory);
        return this.modelMapper.map(foundCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category foundCategory = this.categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", categoryId));
        this.categoryRepo.delete(foundCategory);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories.stream().map((singleCategory) -> this.modelMapper.map(singleCategory, CategoryDTO.class)).toList();
    }
}
