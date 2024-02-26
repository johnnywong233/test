package websocket.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import websocket.bean.SocketMessage;
import websocket.bean.SocketResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/7/25
 * Time: 0:15
 */
@Controller
public class WebSocketController {
    private static final SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //当浏览器向服务器端发送STOMP请求时，通过@MessageMapping注解来映射/getServerTime地址
    @MessageMapping(value = "/getServerTime")
    //当服务端有消息时，会对订阅@SendTo中的路径的客户端发送消息
    @SendTo(value = "/topic/getResponse")
    public SocketResponse serverTime(SocketMessage message) {
        return new SocketResponse(message.getMessage() + SF.format(new Date()));
    }
}
