package com.backend.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String authHeader = request.getURI().getQuery(); // Get the query parameters
        System.out.println("authHeader i.e jwttoken recived during handsake: " + authHeader);
        if (authHeader != null && authHeader.startsWith("token=")) {
            String token = authHeader.substring("token=".length());
            attributes.put("Authorization", token);
            System.out.println("websocket jwt token verfied");
            return true;
        }
        return false;
    }


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                                WebSocketHandler wsHandler, Exception exception) {
        // No-op
    }
}
