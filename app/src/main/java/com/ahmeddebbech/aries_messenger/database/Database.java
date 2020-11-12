package com.ahmeddebbech.aries_messenger.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.ahmeddebbech.aries_messenger.activities.MainActivity;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.activities.LoginActivity;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    Database(){

    }
    public static void connectToSignIn(User user, LoginActivity loginActivity) {
        userExists(user,loginActivity);
    }

    public static void trackUserExistence(final LoggedInUser log, final MainActivity ma){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        ref.orderByChild("uid").equalTo(LoggedInUser.getInstance().getUserModel().getUid()).addValueEventListener(new ValueEventListener() {
            boolean founder = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    founder = true;
                }
                Log.d("$$$$$$$", String.valueOf(founder));
                if (!founder) {
                    LoggedInUser.getInstance().signOut();
                    Intent intent = new Intent(ma, LoginActivity.class);
                    ma.startActivity(intent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                founder = false;
            }
        });
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
                 la.redirectRegisterActivity();
             }else{
                 la.redirectMainActivity();
             }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
            founder = false;
        }
    });
    }
    public static void usernameExists(final String username, final EditText ed){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean founder = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(User.class).getUsername().equals(username)) {
                        founder = true;
                    }
                }
                if (founder) {
                    ed.setError("This username is taken!");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public static void connectToRegister(User user){
        addUserToDatabase(user);
    }

    public static void addUserToDatabase(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+user.getUid());
        ref.setValue(user);
    }

}
