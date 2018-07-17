package ws.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;

/**
 * Author: Johnny
 * Date: 2017/7/25
 * Time: 22:49
 */
public class WSController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg) {
        if ("root".equals(principal.getName())) {
            //向用户发送消息
            messagingTemplate.convertAndSendToUser("johnny",
                    "/point/notifications", principal.getName() + "-send:" + msg);
        } else {
            messagingTemplate.convertAndSendToUser("root",
                    "/point/notifications", principal.getName() + "-send:" + msg);
        }
    }
}
