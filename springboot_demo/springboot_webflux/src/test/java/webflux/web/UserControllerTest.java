package webflux.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import webflux.domain.User;

/**
 * Created by Johnny on 2018/3/16.
 */
public class UserControllerTest {
    private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    //需要 main 方法启动
    @Test
    public void testCreateUser() {
        final User user = new User();
        user.setName("Test");
        user.setEmail("test@example.org");
        client.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("name").isEqualTo("Test");
    }
}