package com.ahmeddebbech.aries_messenger.util;

import android.util.Log;

import java.util.Random;

public class RandomIdGenerator {
    public static String generateMessageId(String convId){
        //id -> coversationId + 5 symbols random
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
    public static String generateConversationId(String userA, String userB){
        //id -> both UIDs with the very fist 5 chars of the two initial members  + 5 rand symbols
        String cut1 = userA.substring(0,4);
        String cut2 = userB.substring(0,4);
        String generatedId = cut1 + cut2 ;
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        String s=(new StringBuilder()).append(generatedId).append(saltStr).toString();
        Log.d("type#$", "conv id" + s);
        return s;
    }
}
