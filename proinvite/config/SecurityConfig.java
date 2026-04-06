package com.proinvite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Definește BCryptPasswordEncoder (pentru criptarea parolelor)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configurează regulile de securitate HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Dezactivează CSRF (necesar pentru API-uri fără sesiuni, cum ar fi cele bazate pe JWT)
                .csrf(AbstractHttpConfigurer::disable)

                // Definește permisiunile de acces
                .authorizeHttpRequests(authorize -> authorize
                        // Permite accesul public la toate căile de autentificare (login, register)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Permite accesul la resursele statice (index.html, JS/CSS)
                        .requestMatchers("/**", "/index.html").permitAll()

                        // Toate celelalte cereri necesită autentificare (le vom bloca/debloca mai târziu)
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}