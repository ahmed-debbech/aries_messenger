package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

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
    public static void connectToSignup(LoggedInUser loggedInUser){
        Database.userExists(loggedInUser);
        /*if(Database.userExists(loggedInUser)){
            return;
        }else {
            Database.addUserToDatabase(loggedInUser);
        }*/
    }
    public static boolean userExists(LoggedInUser loggedInUser){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String phone = ds.child("phone").getValue(String.class);
                    String username = ds.child("username").getValue(String.class);
                    Log.d("####",phone);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(eventListener);
        return true;
    }
    public static void addUserToDatabase(LoggedInUser loggedInUser){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Uid
        DatabaseReference refUid = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/uid");
        refUid.setValue(loggedInUser.getFirebaseUserObject().getUid());
        //username
        DatabaseReference refUsername = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/username");
        refUsername.setValue("@xxxx");
        //display name
        DatabaseReference refDisplayName = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/display-name");
        refDisplayName.setValue(loggedInUser.getFirebaseUserObject().getDisplayName());
        //email
        DatabaseReference refEmail = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/email");
        refEmail.setValue(loggedInUser.getFirebaseUserObject().getEmail());
    }
}
