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
                        .requestMatchers(
                                "/api/emergency/**",   "/auth/login", "/auth/signup","/api/reviews/**","api/notifications/**",
                                "/api/emergency-campaigns/**"
                        ).permitAll()

                        .requestMatchers("/volunteers/**","/volunteer-activities/**","/admin/**").access((authentication, context) -> {
                            Authentication authResult = authentication.get();
                            String userType = (String) authResult.getPrincipal();
                            return new AuthorizationDecision(userType.equals("VOLUNTEER") || userType.equals("ADMIN"));
                        })

                        .requestMatchers("/admin/**","/users/**").access((authentication, context) -> {
                            Authentication authResult = authentication.get();
                            String userType = (String) authResult.getPrincipal();
                            return new AuthorizationDecision(userType.equals("ADMIN"));
                        })

                        .requestMatchers("/admin/**","/api/orphans/**").access((authentication, context) -> {
                            Authentication authResult = authentication.get();
                            String userType = (String) authResult.getPrincipal();
                            return new AuthorizationDecision(userType.equals("ADMIN"));
                        })
                        .requestMatchers("/admin/**","/api/orphan-projects/**").access((authentication, context) -> {
                            Authentication authResult = authentication.get();
                            String userType = (String) authResult.getPrincipal();
                            return new AuthorizationDecision(userType.equals("ADMIN"));
                        })

                        .requestMatchers("/sponsors/**","/api/sponsor-activities/**","/admin/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("SPONSOR")||userType.equals("ADMIN"));
                        })





                        .requestMatchers("/orphanages/**","/admin/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("ORPHANAGE_MANAGER")|| userType.equals("ADMIN"));
                        })

                        .requestMatchers("/donors/**","/admin/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision( userType.equals("ADMIN"));
                        })

                        /*.requestMatchers("/api/donations/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("ADMIN"));
                        })*/
                        .requestMatchers("/api/donations/**","/admin/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("ORPHANAGE_MANAGER")|| userType.equals("ADMIN"));
                        })
                       /* .requestMatchers("/api/emergency/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision( userType.equals("ADMIN"));
                        })*/
                        .anyRequest().authenticated()
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