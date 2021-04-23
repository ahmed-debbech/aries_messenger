package com.ahmeddebbech.aries_messenger.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseReferences {
    public static DatabaseReference REF_USER;
    public static ValueEventListener LIS_USER;

    public static DatabaseReference REF_CONV_META;
    public static ValueEventListener LIS_CONV_META;

    public static DatabaseReference REF_CONV_MEMBERS;
    public static ValueEventListener LIS_CONV_MEMBERS;

    public static DatabaseReference REF_MSGS;
    public static ValueEventListener LIS_MSGS;

}
