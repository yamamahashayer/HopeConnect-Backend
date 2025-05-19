package com.example.HopeConnect.Config;

//import com.sendgrid.http.HttpMethod;
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
import org.springframework.http.HttpMethod;

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

                                "/auth/login", "/auth/signup", "/api/payment/create-checkout-session", "/api/payment/payment-success",
                                "/api/payment/payment-cancel", "/api/reviews/**", "api/notifications/**,"
                        ).permitAll()

                                // السماح للجميع بطلبات POST, PUT, DELETE على /api/locations/**
                                .requestMatchers(HttpMethod.POST, "/api/locations/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/locations/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/locations/**").permitAll()

                                // طلبات GET على نفس المسار لازم تحقق أدوار محددة
                                .requestMatchers(HttpMethod.GET, "/api/locations/**").access((authentication, context) -> {

                            String userType = (String) authentication.get().getPrincipal();
                                    return new AuthorizationDecision(
                                            userType.equals("DONOR") || userType.equals("VOLUNTEER") || userType.equals("ADMIN")
                                    );
                                })

                        /*.requestMatchers(HttpMethod.POST, "/api/locations/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/locations/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/locations/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/locations/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(
                                    userType.equals("DONOR") || userType.equals("VOLUNTEER") || userType.equals("ADMIN")
                            );
                        })*/


                               /* .requestMatchers("/api/payment/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(
                                    userType.equals("DONOR") || userType.equals("VOLUNTEER") || userType.equals("ADMIN")
                            );
                        })*/
                        // ✅ السماح للمسارات التالية حسب الدور
                        .requestMatchers("/api/locations/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(
                                    userType.equals("DONOR") || userType.equals("VOLUNTEER") || userType.equals("ADMIN")
                            );
                        })



                        .requestMatchers("/api/tasks/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(
                                    userType.equals("VOLUNTEER") || userType.equals("ADMIN")
                            );
                        })

                        .requestMatchers("/volunteers/**", "/volunteer-activities/**", "/admin/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("VOLUNTEER") || userType.equals("ADMIN"));
                        })

                        .requestMatchers("/admin/**", "/users/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("ADMIN"));
                        })

                        .requestMatchers("/admin/**", "/api/orphans/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("ADMIN"));
                        })

                        .requestMatchers("/admin/**", "/api/orphan-projects/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("ADMIN"));
                        })

                        .requestMatchers("/sponsors/**", "/api/sponsor-activities/**", "/admin/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("SPONSOR") || userType.equals("ADMIN"));
                        })

                        .requestMatchers("/donors/**", "/admin/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("DONOR") || userType.equals("ADMIN"));
                        })

                        .requestMatchers("/orphanages/**", "/admin/**").access((authentication, context) -> {
                            String userType = (String) authentication.get().getPrincipal();
                            return new AuthorizationDecision(userType.equals("ORPHANAGE_MANAGER") || userType.equals("ADMIN"));
                        })

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
