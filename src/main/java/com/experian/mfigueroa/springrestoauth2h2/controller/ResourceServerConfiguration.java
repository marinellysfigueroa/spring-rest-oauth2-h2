package com.experian.mfigueroa.springrestoauth2h2.controller;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@EnableResourceServer
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure (HttpSecurity http) throws Exception {

        http.cors().and()
       //Permite consultar el token
        .authorizeRequests (). antMatchers ("/oauth/token").permitAll();
        // Solicita autorización para el CRUD de estudiantes"
        http.requestMatchers (). antMatchers ("/students","/students/**").and ().authorizeRequests().antMatchers("/students","/students/**"). access ("hasRole ('USER')");

    }

}
