package com.iss.users;

import com.iss.users.model.Person;
import com.iss.users.model.Role;
import com.iss.users.service.PersonService;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: users
 * @author: 李泰郎
 * @create: 2018-02-27 19:28
 * <p>
 * 该项目是用于将Ignite部署到SpringBoot上的一个测试性的项目
 * 目前的功能包含：
 * 1. 启动并使用一个ignite节点
 * 2. 提供api接口实现RESTful的设计，能够通过api添加与查询Cache中的相关内容
 * <p>
 * It's a test project for deploying Ignite on SpringBoot
 * Function:
 * 1.Start an ignite node
 * 2.provide RESTful api to create or retrieve information in Ignite Cache
 * Here are the apis:
 * /person?name=XXX&phone=XXX	get,	store the person in Ignite and return a json of the person
 * /persons?name=xxx 			get,	return a json of the person
 * <p>
 * It's a test project for deploying Ignite on SpringBoot
 * Function:
 * 1.Start an ignite node
 * 2.provide RESTful api to create or retrieve information in Ignite Cache
 * Here are the apis:
 * /person?name=XXX&phone=XXX	get,	store the person in Ignite and return a json of the person
 * /persons?name=xxx 			get,	return a json of the person
 * <p>
 * It's a test project for deploying Ignite on SpringBoot
 * Function:
 * 1.Start an ignite node
 * 2.provide RESTful api to create or retrieve information in Ignite Cache
 * Here are the apis:
 * /person?name=XXX&phone=XXX	get,	store the person in Ignite and return a json of the person
 * /persons?name=xxx 			get,	return a json of the person
 * <p>
 * It's a test project for deploying Ignite on SpringBoot
 * Function:
 * 1.Start an ignite node
 * 2.provide RESTful api to create or retrieve information in Ignite Cache
 * Here are the apis:
 * /person?name=XXX&phone=XXX	get,	store the person in Ignite and return a json of the person
 * /persons?name=xxx 			get,	return a json of the person
 * <p>
 * It's a test project for deploying Ignite on SpringBoot
 * Function:
 * 1.Start an ignite node
 * 2.provide RESTful api to create or retrieve information in Ignite Cache
 * Here are the apis:
 * /person?name=XXX&phone=XXX	get,	store the person in Ignite and return a json of the person
 * /persons?name=xxx 			get,	return a json of the person
 */

/**
 * It's a test project for deploying Ignite on SpringBoot
 * Function:
 * 	1.Start an ignite node
 * 	2.provide RESTful api to create or retrieve information in Ignite Cache
 * Here are the apis:
 * 		/person?name=XXX&phone=XXX	get,	store the person in Ignite and return a json of the person
 * 		/persons?name=xxx 			get,	return a json of the person
 */

/**
 * 项目启动入口，配置@EnableIgniteRepositories注解以支持ignite的@RepositoryConfig
 */
@SpringBootApplication
//@EnableIgniteRepositories
public class UsersApplication implements CommandLineRunner {

    @Autowired
    PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }


    @Override
    public void run(String... args) throws SQLException, ClassNotFoundException {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.MEMBER);

//        personService.save(new Person("test1", "test11", roles));
//        personService.save(new Person("test2", "test22", roles));
//        personService.save(new Person("test3", "test33", roles));
    }
}
