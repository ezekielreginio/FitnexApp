package com.pupccis.fitnex.handlers.repository;

import android.util.Log;
import android.webkit.MimeTypeMap;

public class ExtensionHandler {
    public static String getExtension(String filetype){
        if(filetype == null){
            Log.e("Missing Data", "Filetype is not found in the instance");
            return null;
        }
        else{
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return  mimeTypeMap.getExtensionFromMimeType(filetype);
        }
    }
}
