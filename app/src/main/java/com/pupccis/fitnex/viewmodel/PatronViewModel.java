package com.pupccis.fitnex.viewmodel;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.model.Patron;
import com.pupccis.fitnex.repository.PatronRepository;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatronViewModel extends BaseObservable {
    //MutableLiveData
    private MutableLiveData<HashMap<Privilege, List<String>>> privilegeListLiveData = new MutableLiveData<>();

    //Repository Instance
    private PatronRepository patronRepository = new PatronRepository();

    //Bindable Attributes
    @Bindable
    private String bronzePricing;
    @Bindable
    private String silverPricing;
    @Bindable
    private String goldPricing;
    @Bindable
    private String customPrivilege;
    @Bindable
    private String sessionNumber;
    @Bindable
    private int currentPrestigeAdded;

    //Private Attributes
    private HashMap<Privilege, List<String>> privilegeList = new HashMap<>();
    private HashMap<Privilege, HashMap<String, Object>> privilegeData = new HashMap<>();
    private List<String> bronzePrivileges =  new ArrayList<>();
    private List<String> silverPrivileges =  new ArrayList<>();
    private List<String> goldPrivileges =  new ArrayList<>();

    private Privilege currentPrivilege;

    //Public Constructor
    public PatronViewModel() {
        initializePrivileges();
    }

    //Getter Methods
    public HashMap<Privilege, List<String>> getPrivilegeList() {
        return privilegeList;
    }

    public MutableLiveData<HashMap<Privilege, List<String>>> getPrivilegeListLiveData() {
        return privilegeListLiveData;
    }

    public int getCurrentPrestigeAdded() {
        return currentPrestigeAdded;
    }

    public String getCustomPrivilege() {
        return customPrivilege;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public String getBronzePricing() {
        return bronzePricing;
    }

    public String getSilverPricing() {
        return silverPricing;
    }

    public String getGoldPricing() {
        return goldPricing;
    }

    //Setter Methods
    public void setCurrentPrivilege(Privilege currentPrivilege) {
        this.currentPrivilege = currentPrivilege;
    }

    public void setCurrentPrestigeAdded(int currentPrestigeAdded) {
        this.currentPrestigeAdded = currentPrestigeAdded;
    }

    public void setCustomPrivilege(String customPrivilege) {
        this.customPrivilege = customPrivilege;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public void setBronzePricing(String bronzePricing) {
        this.bronzePricing = bronzePricing;
        HashMap <String, Object> bronzePricingData = privilegeData.get(Privilege.BRONZE);
        bronzePricingData.put(PatronConstants.KEY_PRICING, Double.parseDouble(bronzePricing));
        privilegeData.put(Privilege.BRONZE, bronzePricingData);
        Log.d("PrivilegeData",privilegeData.toString());
    }

    public void setSilverPricing(String silverPricing) {
        this.silverPricing = silverPricing;
        HashMap <String, Object> silverPricingData = privilegeData.get(Privilege.SILVER);
        silverPricingData.put(PatronConstants.KEY_PRICING, Double.parseDouble(silverPricing));
        privilegeData.put(Privilege.SILVER, silverPricingData);
    }

    public void setGoldPricing(String goldPricing) {
        this.goldPricing = goldPricing;
        HashMap <String, Object> goldPricingData = privilegeData.get(Privilege.GOLD);
        goldPricingData.put(PatronConstants.KEY_PRICING, Double.parseDouble(goldPricing));
        privilegeData.put(Privilege.GOLD, goldPricingData);
    }

    public void setPatronData(HashMap<String, Object> patronDataMap){
        HashMap<String, Object> privilegeMap = (HashMap<String, Object>) patronDataMap.get(PatronConstants.KEY_COLLECTION_PRIVILEGE);

        setPrivilegeData((HashMap<String, Object>) privilegeMap.get(Privilege.BRONZE), bronzePrivileges);
        setPrivilegeData((HashMap<String, Object>) privilegeMap.get(Privilege.SILVER), silverPrivileges);
        setPrivilegeData((HashMap<String, Object>) privilegeMap.get(Privilege.GOLD), goldPrivileges);

        privilegeListLiveData.postValue(privilegeList);
    }

    //Public Methods
    public void initializePrivileges(){

        //Initialize Bronze Privileges
        bronzePrivileges.add("Paid Fitness Programs and Routines (Bronze Level)");
        bronzePrivileges.add("Paid Fitness Training Videos (Bronze Level)");

        //Initialize Silver Privileges
        silverPrivileges.add("Paid Fitness Programs and Routines (Silver Level)");
        silverPrivileges.add("Paid Fitness Training Videos (Silver Level)");

        //Initialize Gold Privileges
        goldPrivileges.add("Paid Fitness Programs and Routines (Gold Level)");
        goldPrivileges.add("Paid Fitness Training Videos (Gold Level)");

        //Initialize Privilege HashMap
        setPrivilegeMap();

        //Initialize Privilege Data;
        setPrivilegeData();
    }

    public void addPrivilege(){
        String newPrivilege = "";
        Log.d("Privilege Data before edit", privilegeData.toString());
        HashMap<String, Object> privilegeDataMap = privilegeData.get(currentPrivilege);
        switch(currentPrestigeAdded){
            case 1:
                newPrivilege = "Free Fitness Training Class Sessions ("+this.sessionNumber+" Sessions)";
                privilegeDataMap.put(PatronConstants.KEY_FITNESS_CLASS_SESSION_NO, this.sessionNumber);
                break;
            case 2:
                newPrivilege = "Free Personal Coaching Sessions ("+this.sessionNumber+" Sessions)";
                privilegeDataMap.put(PatronConstants.KEY_PERSONAL_COACHING_SESSION_NO, this.sessionNumber);
                break;
            case 3:
                newPrivilege = this.customPrivilege;
                List<String> customPrivilegeList = new ArrayList<>();

                if(privilegeDataMap.get(PatronConstants.KEY_CUSTOM_PRIVILEGE) != null)
                    customPrivilegeList = (List<String>) privilegeDataMap.get(PatronConstants.KEY_CUSTOM_PRIVILEGE);

                customPrivilegeList.add(this.customPrivilege);
                privilegeDataMap.put(PatronConstants.KEY_CUSTOM_PRIVILEGE, customPrivilegeList);
                break;
        }
        switch (currentPrivilege){
            case BRONZE:
                bronzePrivileges.add(newPrivilege);
                break;
            case SILVER:
                silverPrivileges.add(newPrivilege);
                break;
            case GOLD:
                goldPrivileges.add(newPrivilege);
                break;
        }
        privilegeData.put(currentPrivilege, privilegeDataMap);
        setPrivilegeMap();
        privilegeListLiveData.postValue(privilegeList);
    }

    //MutableLiveData
    public MutableLiveData<Boolean> checkPatronData(){
        return patronRepository.checkPatronData();
    }

    public MutableLiveData<HashMap<String, Object>> getPatronData(){
        return patronRepository.getPatronDataCheck();
    }

    public MutableLiveData<Boolean> finishPatronSetup(){
        Patron patron = new Patron(System.currentTimeMillis(), privilegeData);
        return patronRepository.insertPatronSetup(patron);
    }

    //Private Methods
    private void setPrivilegeMap(){
        privilegeList.put(Privilege.BRONZE, bronzePrivileges);
        privilegeList.put(Privilege.SILVER, silverPrivileges);
        privilegeList.put(Privilege.GOLD, goldPrivileges);
    }

    private void setPrivilegeData(){
        privilegeData.put(Privilege.BRONZE, new HashMap<>());
        privilegeData.put(Privilege.SILVER, new HashMap<>());
        privilegeData.put(Privilege.GOLD, new HashMap<>());
    }
    private void setPrivilegeData(HashMap<String, Object> privilegeData, List<String> privileges) {
        if(privilegeData.get(PatronConstants.KEY_PERSONAL_COACHING_SESSION_NO) != null)
            privileges.add("Free Personal Coaching Sessions ("+privilegeData.get(PatronConstants.KEY_PERSONAL_COACHING_SESSION_NO)+" Sessions)");
        if(privilegeData.get(PatronConstants.KEY_FITNESS_CLASS_SESSION_NO) != null)
            privileges.add("Free Fitness Training Class Sessions ("+privilegeData.get(PatronConstants.KEY_FITNESS_CLASS_SESSION_NO)+" Sessions)");
        if(privilegeData.get(PatronConstants.KEY_CUSTOM_PRIVILEGE) != null){
            List<String> customPrivilagesList = (List<String>) privilegeData.get(PatronConstants.KEY_CUSTOM_PRIVILEGE);
            for(String customPrivilege : customPrivilagesList)
                privileges.add(customPrivilege);
        }

    }
}
