package com.example.ssh2.demo;

import com.example.ssh2.domain.Book;
import com.example.ssh2.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile(value = {"test-data"})
@Component
public class BookDataLoader {

    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var book1 = Book.of("1234567", "Northern Lights", "Lyra Silverstar", 9.9);
        var book2 = Book.of("1234568", "Polar Journey", "iorek polarson", 23.9);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
