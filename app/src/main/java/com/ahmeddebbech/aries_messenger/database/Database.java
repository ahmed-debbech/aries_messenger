package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.LoggedInUser;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    Database(){

    }
    public static boolean userExists(LoggedInUser loggedInUser){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("users").child(loggedInUser.getFirebaseUserObject().getUid());
        return true;
    }
    public static void addUserToDatabase(LoggedInUser loggedInUser){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/");
        ref.
        ref.setValue(loggedInUser.getFirebaseUserObject().getDisplayName());
    }
}
