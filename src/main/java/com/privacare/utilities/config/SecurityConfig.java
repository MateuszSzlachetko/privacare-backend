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
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain appointmentsSecurityFilterChain(HttpSecurity http) throws Exception {
        return defaultConfiguration(http)
                .securityMatcher(antMatcher("/api/appointments/**"))
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers(regexMatcher(HttpMethod.GET, "\\/api\\/appointments\\?patientId=.*")).authenticated() //done:  check matching uid request-token
                            .requestMatchers(regexMatcher(HttpMethod.GET, "\\/api\\/appointments\\?startDate=.*")).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(regexMatcher(HttpMethod.GET, "\\/api\\/appointments\\?endDate=.*")).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(regexMatcher(HttpMethod.GET, "\\/api\\/appointments\\?slotId=.*")).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.POST).authenticated()  //done:  check matching uid request-token
                            .requestMatchers(regexMatcher(HttpMethod.DELETE, "\\/api\\/appointments\\/multiple?.*")).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/appointments/{id}").authenticated(); //done:  check matching uid request-token
                })
                .build();
    }

    @Bean
    public SecurityFilterChain slotsSecurityFilterChain(HttpSecurity http) throws Exception {
        return defaultConfiguration(http)
                .securityMatcher(antMatcher("/api/slots/**"))
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers(HttpMethod.GET).permitAll()
                            .requestMatchers(HttpMethod.POST).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.PUT).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE).hasAuthority("ROLE_ADMIN");
                })
                .build();
    }

    @Bean
    public SecurityFilterChain usersSecurityFilterChain(HttpSecurity http) throws Exception {
        return defaultConfiguration(http)
                .securityMatcher(antMatcher("/api/users/**"))
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers(regexMatcher(HttpMethod.GET, "\\/api\\/users\\?authId=.*")).authenticated()  //done:  check matching uid request-token
                            .requestMatchers(regexMatcher(HttpMethod.GET, "\\/api\\/users\\?peselFragment=.*")).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(antMatcher(HttpMethod.GET, "/api/users/{id}")).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(antMatcher(HttpMethod.POST, "/api/users")).authenticated(); //done:  check matching uid request-token
                })
                .build();
    }

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
                            .requestMatchers(HttpMethod.POST).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.PUT).hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE).hasAuthority("ROLE_ADMIN")
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