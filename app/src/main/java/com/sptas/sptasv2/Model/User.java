package com.sptas.sptasv2.Model;

/**
 * Created by Na'im Mansor on 06-Feb-18.
 */

public class User {
    private String userType;
    private String userName;
    private String password;
    private String email;
    private String nickName;


    public User() {
    }

    public User(String userType, String userName, String password, String email, String nickName) {
        this.userType = userType;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getnickName() {
        return nickName;
    }

    public void setnickName(String nickName) {
        this.nickName = nickName;
    }
}
