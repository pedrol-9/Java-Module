package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class WebConfig {

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Autowired
  private CorsConfigurationSource corsConfigurationSource;

  @Bean
  public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
    // SecurityFilterChain define un filtro de seguridad que puede procesar una solicitud HTTP en el contexto de la seguridad de la aplicación.

    httpSecurity
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)

            .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

            .authorizeHttpRequests(authorize ->
                    authorize
                            .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/me", "/h2-console/**").permitAll()
                            .requestMatchers("/api/clients/current/accounts", "/api/clients/current/cards", "/api/clients/current/transactions", "/api/loans").hasAnyRole("CLIENT", "ADMIN")
                            .requestMatchers("/api/clients/**", "/api/clients/current").hasRole("ADMIN")
                            .anyRequest().authenticated()
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
    return httpSecurity.build();
  }

  // definición de beans de PasswordEncoder y AuthenticationManager en la configuración de Spring Security.
  @Bean
  public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}