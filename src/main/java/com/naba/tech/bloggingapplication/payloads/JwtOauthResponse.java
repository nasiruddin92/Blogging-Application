package com.naba.tech.bloggingapplication.payloads;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class JwtOauthResponse {
   private String token;
}
