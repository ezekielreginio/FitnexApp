package com.pupccis.fitnex;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String age;
    private String email;
    private String token;

    public User(){

    }

    public User(String name, String age, String email){
        this.setName(name);
        this.setAge(age);
        this.setEmail(email);
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
}