package com.example.padel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // išjungiam CSRF testavimui
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/signup",
                                "/api/auth/login",
                                "/api/auth/verify/**" // pridėk šitą, kad el. pašto nuoroda veiktų
                        ).permitAll() // leidžiam viešus endpointus
                        .anyRequest().authenticated() // kiti endpointai – tik su token
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // išjungiam Basic Auth
                .formLogin(form -> form.disable()); // išjungiam form login

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
