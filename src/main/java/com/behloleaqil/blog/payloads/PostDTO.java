package com.behloleaqil.blog.payloads;

import com.behloleaqil.blog.entities.Category;
import com.behloleaqil.blog.entities.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private int id;
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
    private String imageName;
    private Date addedDate;

    @NotEmpty
    private UserDTO user;

    @NotEmpty
    private CategoryDTO category;

    private List<CommentDTO> comments = new ArrayList<>();
}
