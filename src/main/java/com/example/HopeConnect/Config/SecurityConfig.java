package com.example.HopeConnect.Config;

import com.example.HopeConnect.Config.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth

                        //.requestMatchers("/auth/login", "/auth/signup").permitAll()
                                .anyRequest().permitAll()

//                        .requestMatchers("/admin/**").access((authentication, context) -> {
//                            Authentication authResult = authentication.get();
//                            String userType = (String) authResult.getPrincipal();
//                            return new AuthorizationDecision(userType.equals("ADMIN"));
//                        })
//
//                        .requestMatchers("/volunteers/**").access((authentication, context) -> {
//                            Authentication authResult = authentication.get();
//                            String userType = (String) authResult.getPrincipal();
//                            return new AuthorizationDecision(userType.equals("VOLUNTEER"));
//                        })
//
//                        .requestMatchers("/sponsor/**").access((authentication, context) -> {
//                            Authentication authResult = authentication.get();
//                            String userType = (String) authResult.getPrincipal();
//                            return new AuthorizationDecision(userType.equals("SPONSOR"));
//                        })

                    //   .anyRequest().authenticated()

                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
