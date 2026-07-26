package com.bank.UserEntity;

import com.bank.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bank_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_user_seq")
    @SequenceGenerator(
            name = "bank_user_seq",
            sequenceName = "bank_user_seq",
            allocationSize = 1
    )
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}