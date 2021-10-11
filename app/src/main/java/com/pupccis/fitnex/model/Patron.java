package com.pupccis.fitnex.model;

import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;

import java.io.Serializable;
import java.util.HashMap;

public class Patron implements Serializable {
    private long dateUpdated;
    private HashMap<Privilege, HashMap<String, Object>> privilegeData;
    private Privilege privilege;

    public Patron() {
    }

    public Patron(Privilege privilege){
        this.privilege = privilege;
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

    public HashMap<String, Object> mapPrivilege() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(PatronConstants.KEY_DATE_UPDATED, System.currentTimeMillis());
        map.put(PatronConstants.KEY_COLLECTION_PRIVILEGE, privilege);
        return map;
    }
}
