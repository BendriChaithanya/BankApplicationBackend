package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.UserEntity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}