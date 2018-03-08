package com.sptas.sptasv2.Student.Model;

/**
 * Created by Na'im Mansor on 06-Feb-18.
 */

public class User {
    private String userName;
    private String password;
    private String email;
    private String noPhone;
    private String year;
    private String sv;


    public User() {
    }

    public User(String userName, String password, String email, String noPhone, String year, String sv) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.noPhone = noPhone;
        this.year = year;
        this.sv = sv;
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

    public String getNoPhone() {
        return noPhone;
    }

    public void setNoPhone(String noPhone) {
        this.noPhone = noPhone;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }
}
