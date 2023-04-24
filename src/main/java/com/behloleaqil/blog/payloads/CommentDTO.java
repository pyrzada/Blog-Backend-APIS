package com.behloleaqil.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private int id;
    @NotEmpty
    private String content;
}
