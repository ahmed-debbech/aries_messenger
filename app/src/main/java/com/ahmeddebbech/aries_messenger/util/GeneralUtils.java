package com.ahmeddebbech.aries_messenger.util;

import java.util.Map;

public class GeneralUtils {
    public static boolean twoStringMapsEqual(Map<String, String> l1, Map<String, String> l2){
       if(l1 == null || l2 == null){
           return false;
       }
        return l1.equals(l2);
    }
}
