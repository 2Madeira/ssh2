package com.example.ssh2.service;

import com.example.ssh2.domain.Book;
import com.example.ssh2.domain.BookAlreadyExistsException;
import com.example.ssh2.domain.BookNotFoundException;
import com.example.ssh2.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> bookRepository.save(new Book(existingBook.id(), isbn, book.title(), book.author(), book.price(), existingBook.version(), existingBook.createdDate(), existingBook.lastModifiedDate())))
                .orElseGet(() -> bookRepository.save(book));
    }


}
