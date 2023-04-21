package com.behloleaqil.blog.repositories;

import com.behloleaqil.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
