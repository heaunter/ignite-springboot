package com.iss.users.service.impl;

import com.iss.users.dao.PersonRepository;
import com.iss.users.model.Person;
import com.iss.users.service.PersonService;
import org.apache.ignite.Ignite;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.Query;
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

    //    @Autowired
    private PersonRepository personRepository;

    //    @Autowired
    private IgniteClient client;

    /**
     * Save Person to Ignite DB
     *
     * @param person Person object.
     * @return The Person object saved in Ignite DB.
     */
    public Person save(Person person) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

// Open the JDBC connection.
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1");


        PreparedStatement stmt = conn.prepareStatement("CREATE TABLE Person ( ID INT(11),  Name CHAR(20),  Password CHAR(20),  PRIMARY KEY (ID, Name))");
        stmt.execute();

        /*PreparedStatement*/
        stmt = conn.prepareStatement("MERGE INTO Person(ID, name, Password) VALUES(CAST(? as BIGINT), ?, ?)");
//        PreparedStatement stmt = conn.prepareStatement("DELETE INTO Person(_key, name, CountryCode) VALUES(CAST(? as BIGINT), ?, ?)");

        stmt.setInt(1, (int) person.getId());
        stmt.setString(2, person.getUsername());
        stmt.setString(3, person.getPassword());

        stmt.execute();
        stmt.close();
        conn.close();
        return null;
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

        SqlFieldsQuery query = new SqlFieldsQuery("SELECT * from Person WHERE username=?");
        query.setArgs("test1");
        ClientCache<Object, Object> personCache1 = client.cache("PersonCache");
        FieldsQueryCursor<List<?>> personCache = personCache1.query(query);

        List<List<?>> all = personCache.getAll();
        System.out.println(all);
        return person;
    }

}
