package com.backend.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config)
	{
		// send back to the day to client whose end point have /play
		config.enableSimpleBroker("/play");
		
		// client send data to the end point having prefix play
		//config.setApplicationDestinationPrefixes("/play");
	}
		
	 @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        // Register the WebSocket endpoint that clients will use to connect to your WebSocket server
	        registry.addEndpoint("/ws-message")
	        .setAllowedOrigins("http://localhost:1234")
	        .addInterceptors(new JwtHandshakeInterceptor())
	        .withSockJS();
	        
	    }
	 
	 
}
