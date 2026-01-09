package com.example.demo.dto.user;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String display_name;
}
