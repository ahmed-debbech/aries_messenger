package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.model.SearchItem;
import com.ahmeddebbech.aries_messenger.presenter.MainPresenter;
import com.ahmeddebbech.aries_messenger.presenter.SearchPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.activities.MainActivity;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DbBasic {
    DbBasic(){

    }
    public static void addUserToDatabase(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+user.getUid());
        ref.setValue(user);
    }
    public static void getUserData(final String uid, final MainPresenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users/"+uid);
        System.out.println(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        User u = dataSnapshot.getValue(User.class);
                        System.out.println(u.getDisplayName());
                        UserManager.getInstance().updateWithCopy(u);
                        pres.returnData();
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
    public static void searchAllUsersFromName(final String name, final SearchPresenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SearchItem> list = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.getValue(User.class).getUsername().contains(name)
                    || ds.getValue(User.class).getDisplayName().contains(name)){
                        list.add(new SearchItem(ds.getValue(User.class)));
                    }
                }
                pres.returnDataFromDB(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
