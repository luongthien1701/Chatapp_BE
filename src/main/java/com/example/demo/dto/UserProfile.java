package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfile {
    private Long id;
    private String displayName;
    private String password;
    private String email;
    private String phone;
}
