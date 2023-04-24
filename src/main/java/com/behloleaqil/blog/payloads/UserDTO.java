package com.behloleaqil.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int id;
    @NotEmpty
    @Size(min = 4, message = "Username must be of four characters !!")
    private String name;
    @Email(message = "Email address is not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be of minimum of 3 chars and maximum of 10 chars !!")
    private String password;

    @NotEmpty
    private String about;

    @NotEmpty
    private List<CommentDTO> comments = new ArrayList<>();

}
