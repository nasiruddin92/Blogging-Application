package com.naba.tech.bloggingapplication.repositories;

import com.naba.tech.bloggingapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    User getUserById(long userId);

    Optional<User> findByEmail(String userEmail);
}
