package com.myshop.cmsshoppingcart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());

    }

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
                .antMatchers("/category/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/");
        //                .antMatchers("/category/**").hasAnyRole("USER")


    }
}
