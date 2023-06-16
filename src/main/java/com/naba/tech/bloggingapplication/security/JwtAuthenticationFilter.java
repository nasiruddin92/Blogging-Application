package com.naba.tech.bloggingapplication.security;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if (authHeader==null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken=authHeader.substring(7);
        userEmail=jwtTokenHelper.extractNameFromToken(jwtToken);

        if (userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = customUserDetailService.loadUserByUsername(userEmail);
            if (jwtTokenHelper.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request) );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);

    }
}
