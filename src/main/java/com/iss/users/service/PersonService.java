package com.iss.users.service;

import com.iss.users.model.Person;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for handling SQL operator in PersonCache
 * @program: users
 * @author: 李泰郎
 * @create: 2018-02-27 19:18
 **/

public interface PersonService {
    /**
     *
     * @param person Person Object
     * @return The Person object saved in Ignite DB.
     */
    Person save(Person person) throws ClassNotFoundException, SQLException;

    /**
     * Find a Person from Ignite DB with given name.
     * @param name Person name.
     * @return The person found in Ignite DB
     */
    Person findPersonByUsername(String name);

    List<Person> listByMobile(String mobile);
}
