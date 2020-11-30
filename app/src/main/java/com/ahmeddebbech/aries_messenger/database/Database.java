package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    Database(){

    }
    public static void addUserToDatabase(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+user.getUid());
        ref.setValue(user);
    }
    public static void getUserData(final String uid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        ref.orderByChild("uid").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        LoggedInUser.getInstance().setUserModel(dataSnapshot.getValue(User.class));
                        Log.d(dataSnapshot.getValue(User.class).getUid(), "ggggg");
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
}
