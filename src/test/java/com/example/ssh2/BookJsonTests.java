package com.example.ssh2;

import com.example.ssh2.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> jacksonTester;

    @Test
    public void testSerialize() throws IOException {
        var book = new Book("1234567891011", "Title", "Author", 9.90);
        var jsonContent = jacksonTester.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }

    @Test
    public void testDeserialize() throws IOException {
        var bookInJsonFormat = """
                {
                    "isbn": "123456",
                    "title" : "Test",
                    "author" : "Test",
                    "price" : 7.7
                }
                """;

        Book actualBook = jacksonTester.parse(bookInJsonFormat)
                .getObject();
        assertThat(actualBook)
                .hasFieldOrPropertyWithValue("isbn", "123456")
                .hasFieldOrPropertyWithValue("title", "Test")
                .hasFieldOrPropertyWithValue("author", "Test")
                .hasFieldOrPropertyWithValue("price", 7.7);
    }
}
