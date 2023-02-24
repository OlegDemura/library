package com.demura.library.util;

import com.demura.library.dao.BookDao;
import com.demura.library.model.Book;
import com.demura.library.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class BookValidator implements Validator {

    private final BookDao bookDao;

    public BookValidator(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (bookDao.show(book.getTitle(), book.getAuthor()).isPresent()) {
            errors.rejectValue("title", "", "This book is already exists");
        }
    }
}
