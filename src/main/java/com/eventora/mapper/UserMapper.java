package com.eventora.mapper;

import com.eventora.dto.UserDto;
import com.eventora.dto.UserRequestDto;
import com.eventora.dto.UserUpdateDto;
import com.eventora.entity.UserEntity;

public class UserMapper {

    public static UserDto usermapper(UserEntity userEntity) {
        UserDto udto = new UserDto();
        udto.setUserid(userEntity.getUserid());
        udto.setUsername(userEntity.getUsername());
        udto.setEmail(userEntity.getEmail());
        udto.setUpdatedAt(userEntity.getUpdatedAt());
        udto.setCreatedAt(userEntity.getCreatedAt());
        return udto;
    }

    public static UserEntity userRequestmapper(UserRequestDto userEntity) {
        UserEntity udto = new UserEntity();
        udto.setUsername(userEntity.getUsername());
        udto.setPassword(userEntity.getPassword());
        udto.setEmail(userEntity.getEmail());

        return udto;
    }

    public static UserEntity userUpdateMapper(UserUpdateDto updateDto) {
        UserEntity udto = new UserEntity();
        udto.setUsername(updateDto.getUsername());
        udto.setPassword(updateDto.getPassword());
        return udto;
    }
}
