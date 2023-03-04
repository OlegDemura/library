package com.demura.library.services;

import com.demura.library.model.Book;
import com.demura.library.model.Person;
import com.demura.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findOneBook(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public void free(int id) {
        bookRepository.free(id);
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(value -> {
            value.setPerson(null);
            bookRepository.save(book.get());
        });
    }

    public void saveReader(int id, Person person) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(value -> {
            value.setPerson(person);
            bookRepository.save(book.get());
        });
    }

    public Optional<Book> show(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }
}
