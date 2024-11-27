//package com.backend.websocket;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//import org.springframework.security.web.SecurityFilterChain;
//import static org.springframework.security.config.Customizer.withDefaults;
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig  {
//
//	 @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http
//	          .cors(withDefaults())
//	            .authorizeHttpRequests(authorizeRequests -> 
//	                authorizeRequests
//	                    .requestMatchers("/ws-message/**").permitAll() // Allow all connections to WebSocket endpoints
//	                    .anyRequest().authenticated() // Secure other endpoints
//	            )
//	            .csrf(csrf -> csrf.disable()); // Disable CSRF for WebSocket
//	           
//
//	        return http.build();
//	    }
//	 
//	 
//	 
//}
