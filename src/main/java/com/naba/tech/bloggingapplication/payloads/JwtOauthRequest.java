package com.naba.tech.bloggingapplication.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtOauthRequest {
    private String email;
    private String password;
}
