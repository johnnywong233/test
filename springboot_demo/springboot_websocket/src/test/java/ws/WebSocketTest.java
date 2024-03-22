package ws;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Author: Johnny
 * Date: 2017/7/24
 * Time: 23:33
 */
@Slf4j
@SpringBootTest(classes = {WebSocketApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest {
    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    private final CountDownLatch latch = new CountDownLatch(1);

    private final AtomicReference<Throwable> failure = new AtomicReference<>();

    @LocalServerPort
    private int port = 49170;

    private SockJsClient sockJsClient;

    @BeforeEach
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setup() {
        List transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        this.sockJsClient = new SockJsClient(transports);
    }

    @Test
    public void getGreeting() throws Exception {
        this.sockJsClient.execute(new TestWebSocketHandler(failure), "ws://localhost:" + port + "/sockjs/message?siteId=webtrn&userId=lucy");
        if (latch.await(60, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw new AssertionError("", failure.get());
            }
        } else {
            fail("Greeting not received");
        }
    }

    private class TestWebSocketHandler implements WebSocketHandler {
        private final AtomicReference<?> failure;

        TestWebSocketHandler() {
            this.failure = null;
        }

        TestWebSocketHandler(AtomicReference<?> failure) {
            this.failure = failure;
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            log.info("client connection established");
            session.sendMessage(new TextMessage("hello websocket server!"));
        }

        @Override
        public void handleMessage(@NonNull WebSocketSession session, WebSocketMessage<?> message) {
            String payload = (String) message.getPayload();
            log.info("client handle message: " + payload);
            if (payload.equals("hello websocket client!")) {
                latch.countDown();
            }
            if (payload.equals("web socket notify")) {
                latch.countDown();
            }
        }

        @Override
        public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
            log.info("client transport error");
        }

        @Override
        public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) {
            log.info("client connection closed");
        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }

    }

}