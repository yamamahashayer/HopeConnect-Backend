package com.example.HopeConnect.Config;

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

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().permitAll()
                                /*               .requestMatchers(
                                                       "/auth/login", "/auth/signup"
                                               ).pmitAll()

                                 /*      .requestMatchers("/volunteers/**","/admin/**").access((authentication, context) -> {
                                           Authentication authResult = authentication.get();
                                           String userType = (String) authResult.getPrincipal();
                                           return new AuthorizationDecision(userType.equals("VOLUNTEER") || userType.equals("ADMIN"));
                                       })

                                       .requestMatchers("/admin/**","/users/**").access((authentication, context) -> {
                                           Authentication authResult = authentication.get();
                                           String userType = (String) authResult.getPrincipal();
                                           return new AuthorizationDecision(userType.equals("ADMIN"));
                                       })*/



//                        .requestMatchers("/sponsors/**").access((authentication, context) -> {
//                            String userType = (String) authentication.get().getPrincipal();
//                            return new AuthorizationDecision(userType.equals("SPONSOR"));
//                        })
//
//
//                        .requestMatchers("/donors/**").access((authentication, context) -> {
//                            String userType = (String) authentication.get().getPrincipal();
//                            return new AuthorizationDecision(userType.equals("DONOR"));
//                        })


//                        .requestMatchers("/orphanages/**","/admin/**").access((authentication, context) -> {
//                            String userType = (String) authentication.get().getPrincipal();
//                            return new AuthorizationDecision(userType.equals("ORPHANAGE_MANAGER")|| userType.equals("ADMIN"));
//                        })

                      //  .anyRequest().authenticated()
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
