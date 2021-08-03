package com.iss.users.service.impl;

import com.iss.users.dao.PersonRepository;
import com.iss.users.model.Person;
import com.iss.users.service.PersonService;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Implements interface PersonService
 *
 * @program: users
 * @author: 李泰郎
 * @create: 2018-02-27 19:20
 **/

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Save Person to Ignite DB
     *
     * @param person Person object.
     * @return The Person object saved in Ignite DB.
     */
    public Person save(Person person) {
        return personRepository.save(person.getId(), person);
    }

    /**
     * Find a Person from Ignite DB with given name.
     *
     * @param name Person name.
     * @return The person found in Ignite DB
     */
    public Person findPersonByUsername(String name) {
        Person person = personRepository.findByUsername(name);
        Optional<Person> byId = personRepository.findById(2L);
        return person;
    }

//        personService.save(new Person("name1", "pass11", "18198765432", roles));
//        personService.save(new Person("name2", "pass22", "18198765431", roles));
//        personService.save(new Person("name3", "pass33", "18198765432", roles));

    @Override
    public List<Person> listByMobile(String mobile) {
        List<Person> test33 = personRepository.query("%43%", "name%");
        System.out.println(test33.size());
        return test33;
    }

}
