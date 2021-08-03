package com.iss.users.controller;

import com.alibaba.fastjson.JSONObject;
import com.iss.users.model.Person;
import com.iss.users.model.ReqPerson;
import com.iss.users.model.RespResult;
import com.iss.users.model.Role;
import com.iss.users.service.PersonService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.IgniteClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @program: users
 * @author: 李泰郎
 * @create: 2018-02-27 19:28
 **/
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request) throws UnknownHostException {
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        return "";
    }


    /**
     * User register with whose username and password
     *
     * @param reqPerson
     * @return Success message
     * @throws ServletException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RespResult register(@RequestBody() ReqPerson reqPerson) throws ServletException, SQLException, ClassNotFoundException {
        // Check if username and password is null
        if (reqPerson.getUsername() == "" || reqPerson.getUsername() == null
                || reqPerson.getPassword() == "" || reqPerson.getPassword() == null)
            throw new ServletException("Username or Password invalid!");

        // Check if the username is used
        if (personService.findPersonByUsername(reqPerson.getUsername()) != null)
            throw new ServletException("Username is used!");

        // Give a default role : MEMBER
        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.MEMBER);

        // Create a person in ignite
        personService.save(new Person(reqPerson.getUsername(), reqPerson.getPassword(), "18989876756", roles));

        RespResult result = new RespResult();
        result.setStatuscode("201 CREATED");
        result.setMessage("register success");
        result.setData("");
        return result;
    }

    /**
     * Check user`s login info, then create a jwt token returned to front end
     *
     * @param reqPerson
     * @return jwt token
     * @throws ServletException
     */
    @PostMapping
    public RespResult login(@RequestBody() ReqPerson reqPerson) throws ServletException, SQLException {

        List<Person> people = personService.listByMobile(reqPerson.getMobile());
        Person person = personService.findPersonByUsername(reqPerson.getUsername());
        RespResult result = new RespResult();
        result.setStatuscode("success");
        result.setMessage("success");
        return result;
    }
}
