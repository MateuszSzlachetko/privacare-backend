package com.privacare.utilities.config;

import com.privacare.utilities.security.FireAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain unauthorizedSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .logout().disable()
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers(HttpMethod.POST).authenticated()
                            .and().addFilterBefore(new FireAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
                }).build();

    }

    @Bean
    @Order(1)
    public SecurityFilterChain tasksSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .logout().disable()
                .securityMatcher(antMatcher(HttpMethod.GET, "/api/tasks/**"))
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.GET).permitAll();
                }).build();
    }

    @Bean
    @Order(0)
    public SecurityFilterChain newsSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .logout().disable()
                .securityMatcher(antMatcher(HttpMethod.GET, "/api/news/**"))
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.GET).permitAll();
                }).build();
    }
}