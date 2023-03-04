package com.demura.library.util;

import com.demura.library.model.Person;
import com.demura.library.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personService.show(person.getFio(), person.getYearOfBirthday()).isPresent()) {
            errors.rejectValue("fio", "", "This reader is already exists");
        }
    }
}
