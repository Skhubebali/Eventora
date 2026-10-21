package com.eventora.controller;

import com.eventora.dto.*;
import com.eventora.entity.UserEntity;
import com.eventora.mapper.UserMapper;
import com.eventora.security.JwtService;
import com.eventora.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userv;

    @Autowired
    private JwtService jwtService;


    UserController(UserService userv) {
        this.userv = userv;
    }

    @PostMapping("create-user")

    public UserDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserEntity user = UserMapper.userRequestmapper(userRequestDto);
        UserEntity savedUser = userv.save(user);
        UserDto dto = UserMapper.usermapper(savedUser);
        return dto;

    }

    @GetMapping("get-all")
    public List<UserDto> getAll() {
        List<UserEntity> users = userv.getAllUsers();

        List<UserDto> userdto = new ArrayList<>();
        for (UserEntity u : users) {
            UserDto udto = UserMapper.usermapper(u);

            userdto.add(udto);
        }
        return userdto;
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {

        Optional<UserEntity> user = userv.getById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        UserDto dto = UserMapper.usermapper(userEntity);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PutMapping("update-user/{id}")
    public ResponseEntity<UserDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateDto userUpdateDto) {

        Optional<UserEntity> existingUser = userv.getById(id);

        if (existingUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserEntity updatedUser = userv.update(id, userUpdateDto);

        UserDto dto = UserMapper.usermapper(updatedUser);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {

        Optional<UserEntity> user = userv.getById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userv.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Login

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        UserEntity user = userv.login(loginDto.getEmail(), loginDto.getPassword());

        if (user != null) {
            UserDto userDto = UserMapper.usermapper(user);
            String token = jwtService.createToken(userDto.getEmail());
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setJwtToken(token);
            loginResponseDto.setUserDto(userDto);
            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
