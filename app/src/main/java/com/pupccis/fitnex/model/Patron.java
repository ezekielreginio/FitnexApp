package com.pupccis.fitnex.model;

import com.pupccis.fitnex.api.enums.Privilege;

import java.io.Serializable;
import java.util.HashMap;

public class Patron implements Serializable {
    private long dateUpdated;
    private HashMap<Privilege, HashMap<String, Object>> privilegeData;

    public Patron() {
    }

    public Patron(long dateUpdated, HashMap<Privilege, HashMap<String, Object>> privilegeData) {
        this.dateUpdated = dateUpdated;
        this.privilegeData = privilegeData;
    }

    public long getDateUpdated() {
        return dateUpdated;
    }

    public HashMap<Privilege, HashMap<String, Object>> getPrivilegeData() {
        return privilegeData;
    }

}
