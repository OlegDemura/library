package com.demura.library.dao;

import com.demura.library.model.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;


    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getOneBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Optional<Book> show(String title, String author) {
        return jdbcTemplate.query("SELECT * FROM book WHERE title=? and author=?", new Object[]{title, author}, new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findFirst();
    }

    public List<Book> getBookForPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM book where person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book set title=?, author=?, year=? where id=?", book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void free(int id) {
        jdbcTemplate.update("UPDATE book set person_id = null where id=?", id);
    }

    public void saveReader(int idBook, int idPerson) {
        jdbcTemplate.update("UPDATE book set person_id =? where id=?", idPerson, idBook);
    }
}
