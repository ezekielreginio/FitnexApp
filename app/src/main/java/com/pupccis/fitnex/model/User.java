package com.pupccis.fitnex.model;

import com.pupccis.fitnex.utilities.Constants.UserConstants;

import java.io.Serializable;
import java.util.HashMap;


public class User implements Serializable {

    public enum UserFields{ NAME, EMAIL, AGE, PASSWORD }

    private String name, email, password, userType;
    private int age;
    private String userID, token;

    public User() {
    }

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

    public String getUserType() {
        return userType;
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
        private final String name, email;
        private int age;
        private String userID, password, token, userType;
        public Builder(String name, String email){
            this.name = name;
            this.email = email;
            this.userType = "trainee";
        }
        public Builder(User user){
            this.name = user.getName();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.age = user.getAge();
            this.userID = user.getUserID();
            this.userType = user.getUserType();
            this.token = user.getToken();
        }

        public Builder setUserID(String userID) {
            this.userID = userID;
            return this;
        }

        public Builder setUserType(String userType) {
            this.userType = userType;
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