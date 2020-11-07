package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.activities.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    Database(){

    }
    public static void connectToSignup(User user, LoginActivity la) {
        userExists(user, la);
    }
    public static void userExists(final User user, final LoginActivity la){
    // Get a reference to our posts
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/Users");
    // Attach a listener to read the data at our posts reference
    ref.addListenerForSingleValueEvent(new ValueEventListener() {
        boolean founder = false;
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
             for (DataSnapshot ds : dataSnapshot.getChildren()) {
                 if (ds.getValue(User.class).getEmail().equals(user.getEmail())) {
                     Log.d("LOGIN: ", "found");
                     founder = true;
                 }
             }
             Log.d("LOGIN ", "not found");
             if (!founder) {
                 la.showRegisterActivity();
             }else{
                 la.passToMainActivity();
             }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
            founder = false;
        }
    });
    }
    public static void addUserToDatabase(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+user.getUid());
        ref.setValue(user);
    }
    public static void connectToRegister(User user){
        addUserToDatabase(user);
    }
}
