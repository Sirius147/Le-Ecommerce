package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {
    @NotNull(message = "email cannot be empty")
    @Size(min = 2, message = "email cannot be less than 2 characters")
    @Email
    private String email;
    @NotNull(message = "pwd cannot be empty")
    @Size(min = 8, max = 16, message = "pwd must be equal or greater than 8 chs and less than 16 chs")
    private String pwd;
    @NotNull(message = "name cannot be empty")
    @Size(min = 2, message = "name cannot be less than 2 characters")
    private String name;

}