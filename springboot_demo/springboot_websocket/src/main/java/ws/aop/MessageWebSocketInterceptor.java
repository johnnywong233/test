package ws.aop;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/7/24
 * Time: 23:31
 */
public class MessageWebSocketInterceptor implements HandshakeInterceptor {
    @Override
    @SuppressWarnings({"unchecked"})
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, @NonNull Map attributes) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String siteId = servletRequest.getServletRequest().getParameter("siteId");
            String userId = servletRequest.getServletRequest().getParameter("userId");
            if (siteId == null || userId == null) {
                return false;
            }
            attributes.put("siteId", siteId);
            attributes.put("userId", userId);
        }
        return true;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, Exception exception) {
    }

}
