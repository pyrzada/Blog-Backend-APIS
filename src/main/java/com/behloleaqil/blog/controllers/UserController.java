package com.behloleaqil.blog.controllers;

import com.behloleaqil.blog.payloads.UserDTO;
import com.behloleaqil.blog.services.UserService;
import com.behloleaqil.blog.services.impl.UserServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    public ResponseEntity<UserDTO> userDTOResponseEntity;


    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = this.userServiceImpl.createUser(userDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(
                this.userServiceImpl.getAllUsers(),
                HttpStatus.FOUND);
    }
}
