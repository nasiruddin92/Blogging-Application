package com.naba.tech.bloggingapplication.controllers;


import com.naba.tech.bloggingapplication.payloads.ApiResponse;
import com.naba.tech.bloggingapplication.payloads.UserDto;
import com.naba.tech.bloggingapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto= userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserDto> updateUser(@Valid @RequestBody  UserDto userDto,@PathVariable long userId){
       UserDto updateUserDto=userService.updateUser(userDto,userId);
       return ResponseEntity.ok(updateUserDto);

    }

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUserById(@PathVariable long userId){
        UserDto userDto=userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtos=userService.getAllUsers();
        return ResponseEntity.ok(userDtos);

    }

    @PreAuthorize("hasRole('ADMIN')" )
    @DeleteMapping("/{userId}")
    ResponseEntity<ApiResponse> deleteUserById(@PathVariable long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Delete Successfully !!",true),HttpStatus.OK);
    }
}
