package com.bank.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.bank.dto.UserRequest;
import com.bank.dto.UserResponse;
import com.bank.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // CREATE
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }

    // GET ALL
    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id,
                                   @RequestBody UserRequest request){
        return userService.updateUser(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}