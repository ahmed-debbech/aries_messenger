package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        DatabaseReferences.REF_USER = database.getReference("/Users/"+uid);
        DatabaseReferences.LIS_USER = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    User u = dataSnapshot.getValue(User.class);
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_USER_DATA, u);
                    pres.returnData(doo);
                    //DbBasic.getConnections(uid, pres);
                }else {
                    Log.d(DatabaseOutputKeys.TAG_DB, "[getUserData] User doesn't exist");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[getUserData] operation cancelled due" + databaseError.getCode());
            }
        };
        DatabaseReferences.REF_USER.addListenerForSingleValueEvent(DatabaseReferences.LIS_USER);
    }
    public static void getUserByUid(final String uid, final int flag, final Presenter pres, boolean isSingleEvent){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users/"+uid);
        ValueEventListener val = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    User u = dataSnapshot.getValue(User.class);
                    if(flag == DatabaseOutputKeys.GET_USER_FROM_UID) {
                        DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_USER_FROM_UID, u);
                        pres.returnData(doo);
                    }else{
                        if(flag == DatabaseOutputKeys.GET_USER_TYPING_NAME){
                            DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_USER_TYPING_NAME, u);
                            pres.returnData(doo);
                        }
                    }
                    //DbBasic.getConnections(uid, pres);
                }else {
                    Log.d(DatabaseOutputKeys.TAG_DB, "[getUserByUid] User by thid Uid doesn't exist");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[getUserByUid] operation cancelled due to " + databaseError.getCode());
            }
        };
        if(isSingleEvent == true){
            ref.addListenerForSingleValueEvent(val);
        }else{
            ref.addValueEventListener(val);
        }
    }
    public static void modifyUser(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users/"+user.getUid());
        ref.setValue(user);
    }
    public static void searchAllUsersByName(final String name, final Presenter pres){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<ItemUser> list = new ArrayList<>();
        DatabaseReference ref1 = database.getReference();
        Query o = ref1.child("Users").orderByChild("displayName").startAt(name.toUpperCase()).endAt(name.toLowerCase() + "\uf8ff");
        o.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                   if (ds.getValue(User.class).getDisplayName().toLowerCase().startsWith(name.toLowerCase())) {
                       if(ds.getValue(User.class).getUid().equals(UserManager.getInstance().getUserModel().getUid()) == false){
                           list.add(new ItemUser(ds.getValue(User.class)));
                       }
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[searchAllUsersByName] operation cancelled due to "+error.getCode());
            }
        });
        DatabaseReference ref2 = database.getReference();
        String nname = "";
        if(name.charAt(0) != '@') {
            StringBuilder stringBuilder = new StringBuilder(name);
            stringBuilder.insert(0, '@');
            nname = stringBuilder.toString();
        }
        Query o1 = ref2.child("Users").orderByChild("username").startAt(nname.toUpperCase()).endAt(nname.toLowerCase() + "\uf8ff");
        final String str = nname;
        o1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.getValue(User.class).getUsername().toLowerCase().startsWith(str.toLowerCase())) {
                        if (!list.contains(new ItemUser(ds.getValue(User.class)))) {
                            if(ds.getValue(User.class).getUid().equals(UserManager.getInstance().getUserModel().getUid()) == false){
                                list.add(new ItemUser(ds.getValue(User.class)));
                            }
                        }
                    }
                }
                DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.SEARCH_ALL_USERS_BY_NAME, list);
                pres.returnData(doo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[searchAllUsersByName] operation cancelled due to "+error.getCode());
            }
        });
    }
    public static void convertUidsToUsers(final List<String> uids, final Presenter pres){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final List<ItemUser> users = new ArrayList<>();
        DatabaseReference ref = db.getReference("/Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(String uid : uids) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (uid.equals(ds.getValue(User.class).getUid())) {
                            users.add(new ItemUser(ds.getValue(User.class)));
                        }
                    }
                }
                DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.CONVERT_TO_USERS, users);
                pres.returnData(doo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[convertUidsToUsers] operation cancelled due to "+error.getCode());
            }
        });
    }
    public static void addContact(String uidUser, String addedUid, Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_connections/"+ uidUser+ "/"+addedUid);
        ref.setValue("waiting");
        ref = database.getReference("/Users_connections/"+ addedUid+ "/"+uidUser);
        ref.setValue("pending");
        Boolean b= true;
        DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.ADD_CONTACT, b);
        pres.returnData(doo);
    }
    public static void acceptContact(String uidUser, String addedUid, Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_connections/"+ uidUser+ "/"+addedUid);
        ref.setValue("connected");
        ref = database.getReference("/Users_connections/"+ addedUid+ "/"+uidUser);
        ref.setValue("connected");
        Boolean b= true;
        DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.ACCEPT_CONTACT, b);
        pres.returnData(doo);
    }
    public static void getConnections(final String uid, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_connections/").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Map<String, String> connections = new HashMap<>();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        connections.put(ds.getKey(), ds.getValue(String.class));
                    }
                    if(connections.size() == 0){
                        connections = null;
                    }
                    //Log.d("#@e" , "connections");
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_CONNECTIONS, connections);
                    pres.returnData(doo);
                }else {
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_CONNECTIONS, null);
                    pres.returnData(doo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB,"[getConnections] operation cancelled due to "+ error.getCode());
            }
        });
    }
    public static void removeContact(String uid, String delUid, Presenter pres){
        System.out.println(uid);
        System.out.println(delUid);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_connections/"+uid+"/" + delUid);
        ref.removeValue();
        ref = database.getReference("/Users_connections/"+delUid+"/" + uid);
        ref.removeValue();
        Boolean b = true;
        DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.REMOVE_CONTACT, b);
        pres.returnData(doo);
    }
    public static void setAvailabilityStatus(String uid, int status) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users/"+uid+"/availability");
        ref.setValue(status);
    }
    public static void getConnectionsNumber(String uid, final Presenter pres) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_connections/").child(uid);
        Query o = ref.orderByValue().equalTo(UserManager.CONNECTED);
        o.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long h = snapshot.getChildrenCount();
                DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_CONNECTIONS_NUMBER,h);
                pres.returnData(doo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB,"[getConnectionsNumber] operation cancelled due to "+ error.getCode());
            }
        });

    }
}
