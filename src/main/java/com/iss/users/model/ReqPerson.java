package com.iss.users.model;

/**
 * Used for getting request for login and register
 * Because the request only has username and password info
 *
 * @program: users
 * @author: 李泰郎
 * @create: 2018-03-01 09:44
 **/
public class ReqPerson {

    private int id;

    /**
     * Request username
     */
    private String username;

    private String mobile;

    /**
     * Request password
     */
    private String password;

    public ReqPerson() {
    }

    public ReqPerson(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ReqPerson(String username, String mobile, String password) {
        this.username = username;
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
