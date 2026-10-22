package com.eventora.mapper;

import com.eventora.dto.UserDto;
import com.eventora.dto.UserRequestDto;
import com.eventora.dto.UserUpdateDto;
import com.eventora.entity.UserEntity;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

    public static UserDetails entityToDetails(UserEntity entity) {
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(
                        new SimpleGrantedAuthority(entity.getRole().name())
                );
            }

            @Override
            public @Nullable String getPassword() {
                return entity.getPassword();
            }

            @Override
            public String getUsername() {
                return entity.getEmail();
            }


        };
        return userDetails;
    }
}
