package com.eventora.service;


import com.eventora.config.PasswordConfig;
import com.eventora.dto.UserUpdateDto;
import com.eventora.entity.UserEntity;
import com.eventora.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {


    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepo userrepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userrepo;
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntity save(UserEntity user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
    }


    public UserEntity update(UUID id, UserUpdateDto userUpdateDto) {

        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userUpdateDto.getUsername() != null
                && !userUpdateDto.getUsername().isBlank()) {
            user.setUsername(userUpdateDto.getUsername());

        }

        if (userUpdateDto.getPassword() != null
                && !userUpdateDto.getPassword().isBlank()) {
            String encodedPassword =
                    passwordEncoder.encode(userUpdateDto.getPassword());

            user.setPassword(encodedPassword);
        }

        return userRepo.save(user);
    }


    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<UserEntity> getById(UUID id) {
        return userRepo.findById(id);
    }

    public UserEntity login(String email, String password) {
        Optional<UserEntity> user = userRepo.findByEmail(email);

        if (user.isPresent()) {
            UserEntity u = user.get();
            if (passwordEncoder.matches(password, u.getPassword())) {
                return u;
            }
        }
        return null;
    }

    public void deleteUser(UUID id) {
        userRepo.deleteById(id);
    }


}
