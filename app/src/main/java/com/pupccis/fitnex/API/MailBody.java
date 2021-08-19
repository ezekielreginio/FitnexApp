package com.pupccis.fitnex.API;

public class MailBody {

    private static String email = "fitnex.application@gmail.com";
    private static String password = "fitnexpassword";
    private static String subject = "Welcome to Fitnex";
    private static String body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad\n"+
            "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
            "consequat. Duis aute irure dolor in reprehenderit\n"+
            "in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat " +
            "cupidatat non proident, sunt in culpa qui officia\n"+
            "deserunt mollit anim id est laborum.";

    public MailBody(){

    }

    private void setEmail(String email){
        this.email = email;
    }
    private void setPassword(String password){
        this.password = password;
    }
    private void setSubject(String subject){
        this.subject = subject;
    }
    private void setBody(String body){
        this.body = body;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static String getSubject() {
        return subject;
    }

    public static String getBody() {
        return body;
    }
}
