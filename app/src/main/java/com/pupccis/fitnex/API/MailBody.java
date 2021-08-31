package com.pupccis.fitnex.API;

public class MailBody {

    private static String email = "fitnex.application@gmail.com";
    private static String password = "fitnexpassword";
    private static String subject = "Welcome to Fitnex";
    private static String body = "Welcome to Fitnex!" +
            "I'm Redd, one of the Developers of Fitnex Online Platfore and I'd Like to personally thank you for signing up to our service\n"+
            "We have established Fitnex to promote the health, wellness, and fitness of each individual in our platform " +
            "I'd like to hear everything about you for us to assess your portfolio whether you are qualified to be a Fitnex Trainer\n"+
            "Kindly Send the Following Information as a response to this email:" +
            "Name:\n"+
            "Age:\n"+
            "Occupation:\n"+
            "Supporting Documents and Certifications\n"+
            "We are excited for you to become one of our finest Fitnex trainers!\n"+
            "Sincerely, Redd";

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
