package com.eventora.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name="users")
public class UserEntity {


    @Id
    @Column(name="userid" , unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userid;

    @Column(name="username",nullable = false)
    @NotBlank
    private String username;


    @Column(name="email",nullable = false,unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(name="password",nullable = false)
    @NotBlank
    private String password;

    @Column(name="createdAt",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updatedAt",nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdAt =now;
        updatedAt = now;

    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = LocalDateTime.now();
    }


}
