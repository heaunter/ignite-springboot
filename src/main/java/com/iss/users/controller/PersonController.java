package com.iss.users.controller;

import com.alibaba.fastjson.JSONObject;
import com.iss.users.model.Person;
import com.iss.users.model.ReqPerson;
import com.iss.users.model.RespResult;
import com.iss.users.model.Role;
import com.iss.users.service.PersonService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    @Autowired
    private IgniteClient igniteClient;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request) throws UnknownHostException {
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());
        System.out.println(InetAddress.getLocalHost().getHostAddress());
//        String ip = null;
//
//        //X-Forwarded-For：Squid 服务代理
//        String ipAddresses = request.getHeader("X-Forwarded-For");
//
//        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
//            //Proxy-Client-IP：apache 服务代理
//            ipAddresses = request.getHeader("Proxy-Client-IP");
//        }
//
//        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
//            //WL-Proxy-Client-IP：weblogic 服务代理
//            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
//        }
//
//        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
//            //HTTP_CLIENT_IP：有些代理服务器
//            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
//        }
//
//        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
//            //X-Real-IP：nginx服务代理
//            ipAddresses = request.getHeader("X-Real-IP");
//        }
//
//        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
//        if (ipAddresses != null && ipAddresses.length() != 0) {
//            ip = ipAddresses.split(",")[0];
//        }
//
//        //还是不能获取到，最后再通过request.getRemoteAddr();获取
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
//            ip = request.getRemoteAddr();
//        }
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
        personService.save(new Person(reqPerson.getUsername(), reqPerson.getPassword(), roles));

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

//        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://10.10.20.187");
//
//
//        PreparedStatement stmt1 = conn.prepareStatement("CREATE TABLE ROOM_001 ( ID INT(11),  Name CHAR(20),  Password CHAR(20), Person_id int(20),  PRIMARY KEY (ID))");
//        stmt1.execute();
//        stmt1.close();
//
        long begin = System.currentTimeMillis();
        System.out.println(begin);

//        String SQL = "CREATE TABLE ROOM_344 ( ID INT(11),  Name CHAR(20),  Password CHAR(20), Person_id int(20), PRIMARY KEY (ID));";

//        String SQL = "INSERT INTO ROOM_001(ID, name, Password) VALUES(CAST(? as BIGINT), ?, ?)";
//        SqlFieldsQuery query = new SqlFieldsQuery(SQL);
//        query.setSchema("20001");
//        query.setArgs(reqPerson.getId(), reqPerson.getUsername(), reqPerson.getPassword());
//        FieldsQueryCursor<List<?>> cursor = igniteClient.query(query);
//        Iterator<List<?>> iterator = cursor.iterator();
//        while (iterator.hasNext()) {
//            List<?> next = iterator.next();
//            System.out.println(11);
//        }

//        IntStream.range(1, 5001).forEach(value -> {
//            String SQL = "INSERT INTO ROOM_9292992929229292992929292(ID, name, Password) VALUES(CAST(? as BIGINT), ?, ?)";
//            SqlFieldsQuery query = new SqlFieldsQuery(SQL);
//            query.setArgs(reqPerson.getId() + value, reqPerson.getUsername() + value, reqPerson.getPassword() + value);
//            FieldsQueryCursor<List<?>> cursor = igniteClient.query(query);
//            boolean b = cursor.iterator().hasNext();
//        });


        ClientCacheConfiguration cfg = new ClientCacheConfiguration();
        cfg.setBackups(2);
        cfg.setCacheMode(CacheMode.PARTITIONED);
        cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cfg.setName("exam_101");
        igniteClient.getOrCreateCache(cfg);

        String SQL = "CREATE TABLE ROOM_101 ( ID INT(11),  Name CHAR(20),  Password CHAR(20), Person_id int(20), PRIMARY KEY (ID));";
        SqlFieldsQuery query = new SqlFieldsQuery(SQL);

        FieldsQueryCursor<List<?>> query1 = igniteClient.query(query);
        query1.iterator().hasNext();

//        String SQL = "select id,name,password from ROOM_9292992929229292992929292";
//        SqlFieldsQuery query = new SqlFieldsQuery(SQL);
//        FieldsQueryCursor<List<?>> cursor = igniteClient.query(query);
//        Iterator<List<?>> iterator = cursor.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(JSONObject.toJSONString(iterator.next()));
//        }
//
//        System.out.println(System.currentTimeMillis() - begin);

//      stmt = conn.prepareStatement("insert INTO Person(_key, name, CountryCode) VALUES(CAST(? as BIGINT), ?, ?)");
//                stmt = conn.prepareStatement("SELECT * FROM ROOM_24569929292992 WHERE ID = " + 13271);
//        ResultSet resultSet = stmt.executeQuery();
//        while (resultSet.next()) {
//            long id = resultSet.getLong("id");
//            String name = resultSet.getString("name");
//            String password = resultSet.getString("password");
//
//            System.out.println(id + " " + name + " " + password);
//        }


//        stmt.execute();

//        conn.close();

        return null;
    }
}
