package com.behloleaqil.blog.repositories;

import com.behloleaqil.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
}
