package com.pupccis.fitnex.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String age;
    private String email;
    private String userType;
    private String token;
    private String userID;

    public User(){

    }

    public User(String name, String age, String email, String userType){
        this.setName(name);
        this.setAge(age);
        this.setEmail(email);
        this.setUserType(userType);
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}