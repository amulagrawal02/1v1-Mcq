package com.backend.jwt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;



import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityFilterConfig {

    
    private final JwtAuthenticationEntryPoint point;
    private final JwtAuthenticationFilter filter;

    public SecurityFilterConfig(JwtAuthenticationEntryPoint point, JwtAuthenticationFilter filter) {
		this.point = point;
		this.filter = filter;
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/user/login").permitAll()
                		.requestMatchers("/user/create").permitAll()
                		.requestMatchers("/ws-message/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

	   @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        // Allow origins
	        configuration.setAllowedOrigins(List.of("http://localhost:1234"));
	        // Allow headers
	        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
	        // Allow methods
	        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        
	        // If you want to allow credentials (cookies, authorization headers, etc.):
	        configuration.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
