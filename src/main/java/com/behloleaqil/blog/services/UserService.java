package com.behloleaqil.blog.services;

import com.behloleaqil.blog.payloads.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user, Integer userId);

    UserDTO getUserById(Integer userId);

    List<UserDTO> getAllUsers();

    void deleteUser(Integer userId);
}
