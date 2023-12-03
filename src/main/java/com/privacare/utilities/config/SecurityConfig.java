package com.privacare.utilities.config;

import com.privacare.utilities.security.FireAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public SecurityFilterChain notesSecurityFilterChain(HttpSecurity http) throws Exception {
        return defaultConfiguration(http)
                .securityMatcher(antMatcher("/api/notes/**"))
                .authorizeHttpRequests((authorize) -> {
                    authorize.anyRequest().hasAuthority("ROLE_ADMIN");
                })
                .build();
    }

    @Bean
    public SecurityFilterChain tasksSecurityFilterChain(HttpSecurity http) throws Exception {
        return defaultConfiguration(http)
                .securityMatcher(antMatcher("/api/tasks/**"))
                .authorizeHttpRequests((authorize) -> {
                    authorize.anyRequest().hasAuthority("ROLE_ADMIN");
                })
                .build();
    }

    @Bean
    public SecurityFilterChain newsSecurityFilterChain(HttpSecurity http) throws Exception {
        return defaultConfiguration(http)
                .securityMatcher(antMatcher("/api/news/**"))
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers(HttpMethod.GET).permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/news/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/news/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/news/**").hasAuthority("ROLE_ADMIN")
                            .anyRequest().authenticated();
                })
                .build();
    }

    private HttpSecurity defaultConfiguration(HttpSecurity http) throws Exception {
        return http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .logout().disable()
                .addFilterBefore(new FireAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}