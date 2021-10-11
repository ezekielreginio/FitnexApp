package com.pupccis.fitnex.utilities.Constants;

import com.pupccis.fitnex.api.enums.Privilege;

import java.util.HashMap;

public class PatronConstants {
    //Object Name
    public static final String KEY_COLLECTION_PATRON = "patron";
    public static final String KEY_COLLECTION_PRIVILEGE = "privilege";
    public static final String KEY_COLLECTION_PRIVILEGE_DATA = "privilegeData";
    public static final String KEY_COLLECTION_SUBSCRIPTIONS = "subscriptions";
    public static final String KEY_COLLECTION_SUBSCRIBERS = "subscribers";


    //Object Attributes
    public static final String KEY_DATE_UPDATED = "dateUpdated";
    public static final String KEY_FITNESS_CLASS_SESSION_NO = "fitnessClassSessionNo";
    public static final String KEY_PERSONAL_COACHING_SESSION_NO = "personalCoachingSessionNo";
    public static final String KEY_CUSTOM_PRIVILEGE = "customPrivilege";
    public static final String KEY_PATRON_SET = "patronSet";

    public static final String KEY_TRAINER_ID = "trainerID";
    public static final String KEY_PRICING = "monthlyPrice";

    public static final HashMap<Privilege, Integer> getPrivilegeHashMap(){
        HashMap<Privilege, Integer> privilegeMap = new HashMap<>();
        privilegeMap.put(Privilege.BRONZE, 1);
        privilegeMap.put(Privilege.SILVER, 2);
        privilegeMap.put(Privilege.GOLD, 3);

        return privilegeMap;
    }
}
