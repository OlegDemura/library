package com.demura.library.dao;

import com.demura.library.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setFio(resultSet.getString("fio"));
        person.setYearOfBirthday(resultSet.getInt("yearOfBirthday"));
        return person;
    }
}
