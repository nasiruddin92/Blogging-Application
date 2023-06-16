package com.naba.tech.bloggingapplication.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String AUTHORIZATION_HEADER="Authorization";

    private ApiKey apiKey(){
        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }

    private List<SecurityContext> securityContexts(){
        return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }

    private List<SecurityReference> securityReferences(){
        AuthorizationScope authorizationScope=new AuthorizationScope("global","accessEverything");
        return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{authorizationScope}));
    }

    @Bean
    public Docket api(){
        return new Docket( DocumentationType.SWAGGER_2)
                .select()
                .apis( RequestHandlerSelectors.any())
                .paths( PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My Blogging Application")
                .description("Blogging Application Using Spring Boot")
                .version("1.0.1")
                .build();
    }
}
