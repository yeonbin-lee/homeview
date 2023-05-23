package com.example.demo1.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable()
                .authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/**")).permitAll();

//        request -> request
//                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                .requestMatchers("/api/login","/api/join", "/status").permitAll()
//                .anyRequest().authenticated()
//                .formLogin(login -> login
//                        .loginPage("/api/login")
//                        .loginProcessingUrl("/login-process")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/api/home", true)
//                        .permitAll()
//                )
//                .logout(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
