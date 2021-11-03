package com.pupccis.fitnex.validation;

public class NullValidation {
    public static Double nonNullDouble(Double input){
        if(input == null)
            return 0.0;
        else
            return input;
    }
}
