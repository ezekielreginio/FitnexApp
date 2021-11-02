package com.pupccis.fitnex.api;

import java.util.List;

public class BulletListUtil {
    public static String makeBulletList(List<String> stringList){
        String bulletList = "";

        for(String string : stringList){
            bulletList = bulletList+" \u25CF "+string+"\n";
        }

        return bulletList;
    }

    public static String makeCommaList(List<String> stringList){
        String commaList = "";

        for(int i=0; i<commaList.length(); i++){

        }

        return commaList;
    }
}
