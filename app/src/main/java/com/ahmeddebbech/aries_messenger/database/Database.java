package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.activities.MainActivity;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;

public class Database {
    Database(){

    }
    public static void addUserToDatabase(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+user.getUid());
        ref.setValue(user);
    }
    public static void getUserData(final String uid, final MainActivity act){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users/"+uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        User u = dataSnapshot.getValue(User.class);
                        System.out.println(u.getDisplayName());
                        User.getInstance(u);
                        act.setupUi();
                    }else {
                        Log.d("MISSING USER", "MS");
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public static void modifyUser(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users/"+user.getUid());
        ref.setValue(user);
    }
    public static void getAllUsersFromName(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    User u = new User(ds.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }
}
