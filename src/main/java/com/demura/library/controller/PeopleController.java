package com.demura.library.controller;

import com.demura.library.dao.BookDao;
import com.demura.library.dao.PersonDao;
import com.demura.library.model.Person;
import com.demura.library.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao;
    private final BookDao bookDao;

    private final PersonValidator personValidator;

    public PeopleController(PersonDao personDao, BookDao bookDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.bookDao = bookDao;
        this.personValidator = personValidator;
    }

    //Показать всех
    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", personDao.peopleList());
        return "people/index";
    }

    @GetMapping("/show/{id}")
    private String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getOnePeople(id));
        model.addAttribute("books", bookDao.getBookForPerson(id));
        return "people/show";
    }
    //Показать одного
    @GetMapping("/edit/{id}")
    private String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getOnePeople(id));
        return "people/edit";
    }

    //Создать нового
    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDao.save(person);
        return "redirect:/people";
    }

    //Удалить
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }

    //Обновить
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "people/edit";
        personDao.update(id, person);
        return "redirect:/people";
    }
}
