package com.naba.tech.bloggingapplication.services;

import com.naba.tech.bloggingapplication.entity.User;
import com.naba.tech.bloggingapplication.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createNewRegisterUser(UserDto userDto);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, long userId);
    UserDto getUserById(long userId);
    List<UserDto> getAllUsers();
    void deleteUserById(long userId);
}
