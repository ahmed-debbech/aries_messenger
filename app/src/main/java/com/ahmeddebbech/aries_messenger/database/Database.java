package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;


import com.ahmeddebbech.aries_messenger.LoggedInUser;
import com.ahmeddebbech.aries_messenger.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    Database(){

    }
    public static void connectToSignup(LoggedInUser loggedInUser, LoginActivity la) {
        userExists(loggedInUser, la);
    }
    public static void userExists(final LoggedInUser loggedInUser, final LoginActivity la){
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        // Attach a listener to read the data at our posts reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean founder = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 LoggedInUser.User u = new LoggedInUser.User();
                 for (DataSnapshot ds : dataSnapshot.getChildren()) {
                     Log.d("$$$$$$$$$4", ds.getValue(LoggedInUser.User.class).getEmail());
                     if (ds.getValue(LoggedInUser.User.class).getEmail().equals(loggedInUser.getFirebaseUserObject().getEmail())) {
                         Log.d("LOGIN: ", "found");
                         founder = true;
                     }
                 }
                 Log.d("LOGIN ", "not found");
                 if (!founder) {
                     la.showRegisterActivity();
                 }else{
                     Log.d("ggggggg", "eee0e");
                     la.passToMainActivity();
                     Log.d("ggggggg", "999999999");
                 }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                founder = false;
            }
        });
    }
    public static void addUserToDatabase(LoggedInUser loggedInUser){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/");
        ref.setValue(loggedInUser.getUsr());
    }
    public static void connectToRegister(LoggedInUser loggedInUser){
        addUserToDatabase(loggedInUser);
    }
}
