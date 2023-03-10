package com.demura.library.repository;

import com.demura.library.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByFioAndYearOfBirthday(String fio, int yearOfBirthday);

}
