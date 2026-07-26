package com.bank.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class RegisterRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    private com.bank.enums.Role role; // ADMIN / CUSTOMER
}