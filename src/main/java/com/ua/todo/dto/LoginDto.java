package com.ua.todo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).*$",
            message = "must contain at least one uppercase and lowercase letter")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%^&()_=+{}\\[\\]/|:;,\"<>?]).*$",
            message = "must contain at least one number and special symbol")
    @Pattern(regexp = "^[^А-Яа-яЇїІіЄєҐґЁёЪъЫыЭэ]+$", message = "must contain only latin letters")
    private String password;


}
