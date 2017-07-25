package ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Author: Johnny
 * Date: 2017/7/25
 * Time: 0:17
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    /**
     * register a endpoint with STOMP protocol, and set the mapping url
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/endpointChat").withSockJS();
    }

    /**
     * configure message proxy
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置点对点使用的订阅前缀(客户端订阅路径中需指定)，默认为/user/
        registry.setUserDestinationPrefix("/user/");
        //配置点对点的消息代理, 前缀名自定义,可以同时指定多个前缀来过滤
        registry.enableSimpleBroker("/point");
    }
}
