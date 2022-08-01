package edu.junnikym.chatservice.chat.config;

import edu.junnikym.chatservice.chat.handler.ChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private static final String CHAT_WS_PATH = "/ws/v1/chat";

	@Override
	public void registerWebSocketHandlers (WebSocketHandlerRegistry registry) {
		registry
				.addHandler(chatWebSocketHandler(), CHAT_WS_PATH)
				.setAllowedOrigins("*")
				.addInterceptors(new HttpSessionHandshakeInterceptor())
				.withSockJS();
	}

	@Bean
	public WebSocketHandler chatWebSocketHandler() {
		return new ChatHandler();
	}

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer(){
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		return container;
	}

}
