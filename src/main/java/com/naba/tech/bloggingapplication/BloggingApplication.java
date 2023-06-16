package com.naba.tech.bloggingapplication;

import com.naba.tech.bloggingapplication.config.AppConstraints;
import com.naba.tech.bloggingapplication.entity.Role;
import com.naba.tech.bloggingapplication.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {

		SpringApplication.run(BloggingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Password: "+passwordEncoder.encode("naba123"));

		try {
			Role role=new Role();
			role.setId( AppConstraints.NORMAL_USER);
			role.setName("ROLE_NORMAL");

			Role role1=new Role();
			role1.setId( AppConstraints.ADMIN_USER);
			role1.setName("ROLE_ADMIN");

			List<Role> roles=new ArrayList<>();
			roles.add(role);
			roles.add(role1 );

			roleRepository.saveAll(roles);
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Roles Saved Failed !!");
		}
	}
}
