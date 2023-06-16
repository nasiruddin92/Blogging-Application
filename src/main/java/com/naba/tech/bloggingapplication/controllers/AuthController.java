package com.naba.tech.bloggingapplication.controllers;


import com.naba.tech.bloggingapplication.payloads.JwtOauthRequest;
import com.naba.tech.bloggingapplication.payloads.JwtOauthResponse;
import com.naba.tech.bloggingapplication.payloads.UserDto;
import com.naba.tech.bloggingapplication.security.CustomUserDetailService;
import com.naba.tech.bloggingapplication.security.JwtTokenHelper;
import com.naba.tech.bloggingapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    ResponseEntity<JwtOauthResponse> createToken(@RequestBody JwtOauthRequest jwtOauthRequest){

        //authenticate(jwtOauthRequest.getName(),jwtOauthRequest.getPassword());
        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtOauthRequest.getEmail());

        String generateToken = jwtTokenHelper.generateToken( userDetails );

        JwtOauthResponse response=new JwtOauthResponse();
        response.setToken(generateToken);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/register")
    ResponseEntity<UserDto> createNewRegisterUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createNewRegisterUser(userDto),HttpStatus.CREATED);
    }


}
