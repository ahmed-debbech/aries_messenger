package com.ahmeddebbech.aries_messenger.util;

import android.util.Log;

import java.util.Random;

public class RandomIdGenerator {
    public static String generateMessageId(String convId){
        //TODO : id -> coversationId + 5 symbols random
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        String s=(new StringBuilder()).append(convId).append(saltStr).toString();
        Log.d("type#$", s);
        return s;
    }
    public static String generateConversationId(){
        //TODO : id -> both UIDs with the very fist 5 chars of the two initial members  + 5 rand symbols
        return null;
    }
}
