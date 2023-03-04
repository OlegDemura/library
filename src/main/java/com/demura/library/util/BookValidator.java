package com.demura.library.util;

import com.demura.library.model.Book;
import com.demura.library.services.BookService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (bookService.show(book.getTitle(), book.getAuthor()).isPresent()) {
            errors.rejectValue("title", "", "This book is already exists");
        }
    }
}
