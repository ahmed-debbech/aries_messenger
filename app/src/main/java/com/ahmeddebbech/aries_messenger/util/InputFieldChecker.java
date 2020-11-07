package com.ahmeddebbech.aries_messenger.util;

public class InputFieldChecker {
    public static boolean startsWithAlt(String input){
        if(input.length() == 0){
            return false;
        }
        if(input.charAt(0) == '@'){
            return true;
        }
        return false;
    }
    public static boolean checkLength(String input, int maxLength){
        if(input.length() > maxLength){
            return false;
        }
        return true;
    }
    public static boolean usesOnlyAllowedChars(String input, char allowedSpecialChars[]){
        //allowed chars : '-', '_', '.' and 0..9
        if(input.length() >= 1) {
            for (int i = 0; i <= input.length() - 1; i++) {
                if(('z' < input.charAt(i) || input.charAt(i) <'a')
                && ('Z' < input.charAt(i) || input.charAt(i) < 'A')
                && ('9' < input.charAt(i) || input.charAt(i) < '0')){
                    boolean t = false;
                    for(int j=0; j<=allowedSpecialChars.length-1; j++){
                        if(input.charAt(i) == allowedSpecialChars[j]){
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
