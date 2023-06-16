package com.naba.tech.bloggingapplication.services.Impl;

import com.naba.tech.bloggingapplication.config.AppConstraints;
import com.naba.tech.bloggingapplication.entity.Role;
import com.naba.tech.bloggingapplication.entity.User;
import com.naba.tech.bloggingapplication.exceptions.ResourceNotFoundException;
import com.naba.tech.bloggingapplication.payloads.UserDto;
import com.naba.tech.bloggingapplication.repositories.RoleRepository;
import com.naba.tech.bloggingapplication.repositories.UserRepository;
import com.naba.tech.bloggingapplication.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createNewRegisterUser( UserDto userDto) {

        User user = modelMapper.map( userDto, User.class );
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.getRoles().add(roleRepository.findById( AppConstraints.NORMAL_USER).get());
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    @Override
    public UserDto createUser( UserDto userDto) {

        User user=userDtoToUser(userDto);
        User saveUser=userRepository.save(user);
        return userToUserDto(saveUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {

        User user=userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());

        User updateUser=userRepository.save(user);
        return userToUserDto(updateUser);
    }

    @Override
    public UserDto getUserById(long userId) {

        User user=userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        return userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users=userRepository.findAll();
//        List<UserDto> userDtos =new ArrayList<>(  );
//
//        for (User user:users) {
//            userDtos.add(userToUserDto(user));
//        }

        return users.stream().map( this::userToUserDto ).collect( Collectors.toList() );

    }

    @Override
    public void deleteUserById(long userId) {
        User user= userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        userRepository.delete(user);

    }

    private User userDtoToUser(UserDto userDto){
        return modelMapper.map(userDto,User.class);
//        User user=new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
//
//        return user;
    }

    private UserDto userToUserDto(User user){
        return modelMapper.map(user,UserDto.class);

//        UserDto userDto=new UserDto();
//
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
//
//        return userDto;
    }
}
