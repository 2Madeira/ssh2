package com.example.ssh2;

import com.example.ssh2.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
class Ssh2ApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenPostRequestThenBookCreated() {
        var expectedBook = Book.of("1234567891011", "Title", "Author", 9.90, "Polarsophia");
        webTestClient.post().uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CREATED)
                .expectBody(Book.class)
                .value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });

    }

    @Test
    void contextLoads() {
    }

}
