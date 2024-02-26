package ws.aop;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Author: Johnny
 * Date: 2017/7/24
 * Time: 23:32
 */
public class MessageWebSocketHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) {
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) {
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
