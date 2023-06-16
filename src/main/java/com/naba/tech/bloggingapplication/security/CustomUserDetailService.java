package com.naba.tech.bloggingapplication.security;

import com.naba.tech.bloggingapplication.entity.User;
import com.naba.tech.bloggingapplication.exceptions.ResourceNotFoundException;
import com.naba.tech.bloggingapplication.repositories.UserRepository;
import com.naba.tech.bloggingapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepository.findByEmail(username ).orElseThrow( () -> new ResourceNotFoundException( "User", "UserName: " + username, 0));
    }
}
