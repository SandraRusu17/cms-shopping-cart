package com.myshop.cmsshoppingcart.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // you should go from allowing less, to allowing more
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // using Spring Expression Language (SpEL)
//        http
//                .authorizeRequests()
//                .antMatchers("/category/**").access("hasRole('ROLE_USER')")
//                .antMatchers("/").access("permitAll");
        http
                .authorizeRequests()
                .antMatchers("/category/**").hasAnyRole("USER")
                .antMatchers("/").permitAll();

    }
}
