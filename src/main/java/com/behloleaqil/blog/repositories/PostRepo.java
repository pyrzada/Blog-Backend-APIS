package com.behloleaqil.blog.repositories;

import com.behloleaqil.blog.entities.Category;
import com.behloleaqil.blog.entities.Post;
import com.behloleaqil.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findAllByUser(User user);

    List<Post> findAllByCategory(Category category);
}
