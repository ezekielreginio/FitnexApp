package com.pupccis.fitnex.api;

public class MailBody {

    private static String email = "fitnex.application@gmail.com";
    private static String password = "fitnexpassword";
    private static String subject = "Fitnex Application Received! - First Step to Fitness Greatness";
    private static String body = "" +
            "Greetings Fitnex Trainer Applicant!\n\n"+
            "First of all, we would like to express our humble gratitude for your kind consideration in applying to Fitnex as a Fitness Trainer/Specialist."+
            "\n\nI'm Redd, one of the Developers of Fitnex Online Platform and I'd Like to personally thank you for signing up to our online fitness platform. "+
            "We have established Fitnex to promote the health, wellness, and fitness of each individual in our platform " +
            "\n\nRest assured that our System Administrators are processing your application and you will be hearing from us very soon. Kindly wait for our email response for the result of your application assessment."+
            "\n\nWe are looking forward to welcome you in Fitnex and work with you as soon as possible"+
            "\n\nSincerely, Redd";

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
