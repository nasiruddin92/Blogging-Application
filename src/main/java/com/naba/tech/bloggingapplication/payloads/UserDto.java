package com.naba.tech.bloggingapplication.payloads;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private long id;

    @NotEmpty
    @Size(min =4,max =40,message ="User Name must be between 4 to 40 character")
    private String name;

    @Email(message ="Your given email address is not valid!!")
    private String email;

    @NotEmpty
    @Size(min=3,max = 10,message = "Password must be 3 to 10 character")
    private String password;
    @NotEmpty
    private String about;

    private Set<RoleDto> roles=new HashSet<>();
}
