package com.ahmeddebbech.aries_messenger.util;

import android.util.Log;

public class InputFieldChecker {
    public static boolean noSpaces(String input){
        if(input.contains(" ") == true){
            return false;
        }
        return true;
    }
    public static boolean startsWithAlt(String input){
        if(input.length() == 0){
            return false;
        }
        if(input.charAt(0) == '@'){
            return true;
        }
        return false;
    }
    public static boolean isLonger(String input, int maxLength){
        if(input.length() > maxLength){
            return true;
        }
        return false;
    }
    public static boolean usesOnlyAllowedChars(String input, char allowedSpecialChars[]){
        //allowed chars : '-', '_', '.' and 0..9
        if(input.length() >= 1) {
            String g = input.substring(1);
            Log.d("$$$$$$", g);
            for (int i = 0; i <= g.length() - 1; i++) {
                if(('z' < g.charAt(i) || g.charAt(i) <'a')
                && ('Z' < g.charAt(i) || g.charAt(i) < 'A')
                && ('9' < g.charAt(i) || g.charAt(i) < '0')){
                    boolean t = false;
                    for(int j=0; j<=allowedSpecialChars.length-1; j++){
                        if(g.charAt(i) == allowedSpecialChars[j]){
                            t= true;
                        }
                    }
                    if(t == false){
                        return false;
                    }
                }
            }
        }else {
            return false;
        }
        return true;
    }
}
