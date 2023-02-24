package com.demura.library.controller;

import com.demura.library.dao.BookDao;
import com.demura.library.dao.PersonDao;
import com.demura.library.model.Book;
import com.demura.library.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String getAllBook(Model model) {
        model.addAttribute("books", bookDao.getAllBooks());
        return "book/index";
    }

    @GetMapping("/show/{id}")
    public String getOneBook(@PathVariable("id") int id, Model model,
                             @ModelAttribute("person") Person person
    ) {
        model.addAttribute("book", bookDao.getOneBook(id));
        model.addAttribute("people", personDao.peopleList());
        return "book/show";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getOneBook(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "book/edit";
        bookDao.update(id, book);
        return "redirect:/book";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "book/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "people/new";
        bookDao.save(book);
        return "redirect:/book";
    }

    @GetMapping("/free/{id}")
    public String freeBook(@PathVariable("id") int id) {
        bookDao.free(id);
        return "redirect:/book";
    }

    @PatchMapping("/addreader/{id}")
    public String addReader(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDao.saveReader(id, person.getId());
        return "redirect:/book";
    }
}
