package com.demura.library.dao;

import com.demura.library.model.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> peopleList() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getOnePeople(int id) {
        return jdbcTemplate.query("SELECT * FROM person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream()
                .findFirst()
                .orElse(null);
    }

    public Optional<Person> show(String fio, int yearOfBirthday) {
        return jdbcTemplate.query("SELECT * FROM person where fio=? and yearofbirthday=?", new Object[]{fio, yearOfBirthday}, new BeanPropertyRowMapper<>(Person.class)).stream()
                .findFirst();
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person where id=?", id);
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person set fio=?, yearofbirthday=? where id=?", person.getFio(), person.getYearOfBirthday(), id);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(fio, yearofbirthday) VALUES (?, ?)", person.getFio(), person.getYearOfBirthday());
    }


}
