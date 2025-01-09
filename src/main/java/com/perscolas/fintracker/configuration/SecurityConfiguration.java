package com.perscolas.fintracker.configuration;

import com.perscolas.fintracker.servise.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * Security configuration class that sets up authentication and authorization for the application.
 * - Configures a custom DaoAuthenticationProvider with BCrypt password encoding.
 * - Defines permitted URL paths (e.g., login, create-account, static resources) for public access.
 * - Sets up role-based authorization, where the "/**" path requires "USER" role.
 * - Customizes login and logout behavior, including success and failure URLs.
 * - Provides a bean to retrieve the current authentication object.
 */
@Component
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String LOGIN_URL = "/login";
    private static final String[] PERMIT_ALL_URL = {LOGIN_URL, "/", "/create-account",
            "/resources/**", "/css/**", "/img/**", "/js/**"};

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_ALL_URL).permitAll()
                        .requestMatchers("/**").hasRole("USER")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage(LOGIN_URL)
                        .loginProcessingUrl(LOGIN_URL)
                        .successForwardUrl("/dashboard")
                        .failureForwardUrl(LOGIN_URL)
                        .permitAll())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }

    @Bean
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
