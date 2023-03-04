package com.demura.library.services;

import com.demura.library.model.Person;
import com.demura.library.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAllPerson() {
        return personRepository.findAll();
    }

    public Person findOnePerson(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person book) {
        personRepository.save(book);
    }

    @Transactional
    public void update(int id, Person book) {
        book.setId(id);
        personRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> show(String fio, int yearOfBirthday){
        return personRepository.findByFioAndYearOfBirthday(fio, yearOfBirthday);
    }
}
