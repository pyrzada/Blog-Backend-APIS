package com.behloleaqil.blog.services.impl;

import com.behloleaqil.blog.entities.User;
import com.behloleaqil.blog.exceptions.ResourceNotFoundException;
import com.behloleaqil.blog.payloads.UserDTO;
import com.behloleaqil.blog.repositories.UserRepo;
import com.behloleaqil.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO user) {
        User savedUser = this.userRepo.save(this.dtoToUser(user));
        return this.userToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "ID", userId)
        );
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        User updatedUser = this.userRepo.save(user);
        return this.userToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "ID", userId)
        );
        return this.userToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(this::userToDTO).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "ID", userId)
        );
        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }

    private UserDTO userToDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }
}
