package com.pupccis.fitnex.Model;

import com.pupccis.fitnex.Utilities.Constants.UserConstants;

import java.io.Serializable;
import java.util.HashMap;


public class User implements Serializable {

    private final String name, email, password, userType;
    private final int age;
    private String userID, token;

    public User(Builder builder){
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.age = builder.age;
        this.userID = builder.userID;
        this.userType = builder.userType;
        this.token = builder.token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getUserID() {
        return userID;
    }

    public String getToken(){
        return token;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(UserConstants.KEY_USER_NAME, name);
        map.put(UserConstants.KEY_USER_AGE, age);
        map.put(UserConstants.KEY_USER_EMAIL, email);
        map.put(UserConstants.KEY_USER_TYPE, userType);
        return map;
    }


    public static class Builder{
        private final String name, email, userType;
        private int age;
        private String userID, password, token;
        public Builder(String name, String email){
            this.name = name;
            this.email = email;
            this.userType = "trainee";
        }

        public Builder setUserID(String userID) {
            this.userID = userID;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public User build(){
            User user = new User(this);
            return user;
        }
    }
}