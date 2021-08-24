package com.pupccis.fitnex.Models;

public class Class {

    private String timeStart;
    private String timeEnd;
    private String className;
    private String sessionNo;


    public Class() {
    }

    public Class( String className, String timeStart, String timeEnd, String sessionNo) {
        this.className = className;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sessionNo = sessionNo;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }
}
