package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.model.SearchItem;
import com.ahmeddebbech.aries_messenger.presenter.MainPresenter;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
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
import java.util.List;

public class DbBasic {
    DbBasic(){

    }
    public static void addUserToDatabase(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Users/"+user.getUid());
        ref.setValue(user);
    }
    public static void getUserData(final String uid, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users/"+uid);
        System.out.println(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        User u = dataSnapshot.getValue(User.class);
                        System.out.println(u.getDisplayName());
                        Log.d("MISSIeeeeNG USER", "MS");
                        pres.returnData(u);
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
    public static void searchAllUsersFromName(final String name, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SearchItem> list = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    String user;
                    if(name.charAt(0) != '@') {
                        user = ds.getValue(User.class).getUsername().substring(1);
                    }else{
                        user = ds.getValue(User.class).getUsername();
                    }
                    String disp = ds.getValue(User.class).getDisplayName();
                    if(user.toLowerCase().startsWith(name) || disp.toLowerCase().startsWith(name)){
                        System.out.println("user "+ ds.getValue(User.class).getDisplayName());
                        list.add(new SearchItem(ds.getValue(User.class)));
                    }
                }
                pres.returnData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void addContact(String uidUser, String addedUid, Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_connections/" + uidUser);
        List<Integer> g = new ArrayList<Integer>();
        g.add(new Integer(1));
        g.add(new Integer(2));
        g.add(new Integer(3));
        ref.setValue(g);
        Boolean b = true;
        pres.returnData(b);
    }
}
