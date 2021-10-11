package com.pupccis.fitnex.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HealthAssessment {
    private String height;
    private String weight;
    private List<String> allergies;
    private List<String> comorbidities;
    public HealthAssessment() {
    }
    public HealthAssessment(Builder builder) {
        this.height = builder.height;
        this.weight = builder.weight;
        this.allergies = builder.allergies;
        this.comorbidities = builder.comorbidities;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("height", height);
        map.put("weight", weight);
        map.put("allergies", allergies);
        map.put("comorbidities", comorbidities);
        return map;
    }

    public static class Builder{
        private String height;
        private String weight;
        private List<String> allergies;
        private List<String> comorbidities;
        public Builder(String height, String weight, List<String> allergies, List<String> comorbidities){
            this.height = height;
            this.weight = weight;
            this.allergies = allergies;
            this.comorbidities = comorbidities;
        }
        public HealthAssessment build(){
            HealthAssessment healthAssessment = new HealthAssessment(this);
            return healthAssessment;
        }
    }

}
