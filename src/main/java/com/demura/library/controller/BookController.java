package com.demura.library.controller;

import com.demura.library.model.Book;
import com.demura.library.model.Person;
import com.demura.library.services.BookService;
import com.demura.library.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final PersonService personService;
    private final BookService bookService;

    public BookController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getAllBook(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "book/index";
    }

    @GetMapping("/show/{id}")
    public String getOneBook(@PathVariable("id") int id, Model model,
                             @ModelAttribute("person") Person person
    ) {
        model.addAttribute("book", bookService.findOneBook(id));
        model.addAttribute("people", personService.findAllPerson());
        return "book/show";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOneBook(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "book/edit";
        bookService.update(id, book);
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
        bookService.save(book);
        return "redirect:/book";
    }

    @GetMapping("/free/{id}")
    public String freeBook(@PathVariable("id") int id) {
        bookService.free(id);
        return "redirect:/book";
    }

    @PatchMapping("/addreader/{id}")
    public String addReader(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookService.saveReader(id, person);
        return "redirect:/book";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book";
    }
}
