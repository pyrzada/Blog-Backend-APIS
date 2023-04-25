package com.behloleaqil.blog.security;

import com.behloleaqil.blog.exceptions.ResourceNotFoundException;
import com.behloleaqil.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.behloleaqil.blog.entities.User;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * Loading User from database by username
         */

        return this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email :" + username, 0));
    }
}
