package ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ws.aop.MessageWebSocketHandler;
import ws.aop.MessageWebSocketInterceptor;

/**
 * Author: Johnny
 * Date: 2017/7/24
 * Time: 23:29
 */
@Configuration
@EnableWebSocket
public class MessageWebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageWebSocketHandler(), "/sockjs/message")
                .addInterceptors(new MessageWebSocketInterceptor()).withSockJS();
    }
    @Bean
    public MessageWebSocketHandler messageWebSocketHandler() {
        return new MessageWebSocketHandler();
    }
}
