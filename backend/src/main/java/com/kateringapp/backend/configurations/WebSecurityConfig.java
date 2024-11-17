package com.kateringapp.backend.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests((authorize) ->
            authorize
                    .requestMatchers(HttpMethod.GET, "/helloWorld").permitAll()
                    .requestMatchers(HttpMethod.GET, "/secured").authenticated()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .anyRequest().permitAll()
            ).oauth2ResourceServer(
                    oauth2 -> oauth2.jwt(Customizer.withDefaults())
            ).cors(
                    corsSpec -> corsSpec.configurationSource(corsConfigurationSource)
            ).csrf(
                    AbstractHttpConfigurer::disable
            )
                .headers(AbstractHttpConfigurer::disable).build();
    }
}
