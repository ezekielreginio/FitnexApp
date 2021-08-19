package com.pupccis.fitnex;

import java.io.Serializable;

public class User implements Serializable {

    public String name, age, email, token;

    public User(){

    }

    public User(String name, String age, String email){
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName(){
        return this.name;
    }
}