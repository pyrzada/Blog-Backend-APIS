package com.behloleaqil.blog.controllers;

import com.behloleaqil.blog.payloads.APIResponse;
import com.behloleaqil.blog.payloads.UserDTO;
import com.behloleaqil.blog.services.UserService;
import com.behloleaqil.blog.services.impl.UserServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    public ResponseEntity<UserDTO> userDTOResponseEntity;


    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = this.userService.createUser(userDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(
                this.userService.getAllUsers(),
                HttpStatus.FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(
                this.userService.getUserById(userId),
                HttpStatus.FOUND);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Integer userId) {
        UserDTO createdUserDTO = this.userService.updateUser(userDTO, userId);
        return ResponseEntity.ok(createdUserDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new APIResponse("User Deleted Successfully", true), HttpStatus.OK);
    }
}
