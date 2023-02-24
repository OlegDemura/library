package com.demura.library.util;

import com.demura.library.dao.PersonDao;
import com.demura.library.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {

    private final PersonDao personDao;

    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personDao.show(person.getFio(), person.getYearOfBirthday()).isPresent()) {
            errors.rejectValue("fio", "", "This reader is already exists");
        }
    }
}
