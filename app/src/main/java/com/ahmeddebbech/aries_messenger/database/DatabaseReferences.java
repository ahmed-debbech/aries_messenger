package com.ahmeddebbech.aries_messenger.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseReferences {
    public static int MAX_CONVS_LISTENERS = 2000;
    public static DatabaseReference REF_USER;
    public static ValueEventListener LIS_USER;

    public static DatabaseReference REF_CONV_META;
    public static ValueEventListener LIS_CONV_META;

    public static DatabaseReference REF_CONV_MEMBERS;
    public static ValueEventListener LIS_CONV_MEMBERS;

    public static DatabaseReference REF_MSGS;
    public static ValueEventListener LIS_MSGS;

    public static DatabaseReference REF_WHOS_TYPING;
    public static ValueEventListener LIS_WHOS_TYPING;

    public static DatabaseReference[] REF_ALL_CONVS = new DatabaseReference[MAX_CONVS_LISTENERS];
    public static ValueEventListener LIS_ALL_CONVS[] = new ValueEventListener[MAX_CONVS_LISTENERS];

    public static void removeConvListener(){
        DatabaseReferences.REF_CONV_META.removeEventListener(DatabaseReferences.LIS_CONV_META);
        DatabaseReferences.REF_CONV_MEMBERS.removeEventListener(DatabaseReferences.LIS_CONV_MEMBERS);
        DatabaseReferences.REF_MSGS.removeEventListener(DatabaseReferences.LIS_MSGS);
        DatabaseReferences.REF_WHOS_TYPING.removeEventListener(DatabaseReferences.LIS_WHOS_TYPING);
    }
    public static void removeAllConvsListeners(){
        for(int i=0; i<=DatabaseReferences.REF_ALL_CONVS.length-1; i++){
            DatabaseReferences.REF_ALL_CONVS[i].removeEventListener(DatabaseReferences.LIS_ALL_CONVS[i]);
        }
    }
}
