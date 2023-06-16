package com.naba.tech.bloggingapplication.repositories;

import com.naba.tech.bloggingapplication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Long> {
}
