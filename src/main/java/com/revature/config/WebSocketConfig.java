package com.revature.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        //definition of messages with destination locatipn / tpoic
        config.enableSimpleBroker("/topic");
        //messages whose destination starts with /app
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //establishing endpoint to talk to websocket server for [S]imple [T]ext [O]riented [M]essaging Protocol.
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }
}
