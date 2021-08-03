package com.iss.users.dao;

import com.iss.users.model.Person;
import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.Query;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Apache Ignite Spring Data repository backed by Ignite Person's cache.
 *
 * @program: users
 * @author: 李泰郎
 * @create: 2018-02-27 18:50
 **/

@RepositoryConfig(cacheName = "PersonCache")
public interface PersonRepository extends IgniteRepository<Person, Long> {

    /**
     * Find a person with given name in Ignite DB.
     *
     * @param name Person name.
     * @return The person whose name is the given name.
     */
    Person findByUsername(String name);

    List<Person> findByMobile(String mobile);

    @Query(value = "select * from PERSON where PERSON.mobile like ? and PERSON.username like ?", distributedJoins = true)
    List<Person> query(String mobile, String name);


}
