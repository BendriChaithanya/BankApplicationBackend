package com.bank.dto;

import com.bank.enums.Role;
import lombok.Data;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private Role role;

}