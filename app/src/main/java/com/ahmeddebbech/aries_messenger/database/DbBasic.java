package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.AriesError;
import com.ahmeddebbech.aries_messenger.model.Feedback;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.UserItemPresenter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<ArrayList<ItemUser>> call = DbConnector.backendServiceApi.searchAllUsersByName(UserManager.getInstance().getAccessToken(), name);

        call.enqueue(new Callback<ArrayList<ItemUser>>() {
            @Override
            public void onResponse(Call<ArrayList<ItemUser>> call, Response<ArrayList<ItemUser>> response) {
                if(!response.isSuccessful()){
                    Log.d(DatabaseOutputKeys.TAG_DB, "[searchAllUsersByName] : the operation was not successful");
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.ANY_ERROR, new AriesError("The search operation was not successful"));
                    pres.returnData(doo);
                    return;
                }
                ArrayList<ItemUser> list = new ArrayList<>();
                List<ItemUser> users = response.body();

                for(ItemUser i : users){
                    list.add(i);
                }
                DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.SEARCH_ALL_USERS_BY_NAME, list);
                pres.returnData(doo);
            }

            @Override
            public void onFailure(Call<ArrayList<ItemUser>> call, Throwable t) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[searchAllUsersByName] : could not get users");
                DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.ANY_ERROR, new AriesError("The search operation was not successful"));
                pres.returnData(doo);
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
                if(uids != null) {
                    for (String uid : uids) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (uid.equals(ds.getValue(User.class).getUid())) {
                                users.add(new ItemUser(ds.getValue(User.class)));
                            }
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

    public static void unblockConnection(String uid, String uid1, UserItemPresenter userItemPresenter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/blocked_users/"+uid+"/"+uid1);
        ref.removeValue();
        ref = database.getReference("/blocked_users/"+uid1+"/"+uid);
        ref.removeValue();
    }

    public static void blockConnection(String uid, String uid1) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/blocked_users/"+uid+"/"+uid1);
        ref.setValue("block");
        ref = database.getReference("/Users_connections/"+uid1+"/"+uid);
        ref.removeValue();
        ref = database.getReference("/Users_connections/"+uid+"/"+uid1);
        ref.removeValue();
        ref = database.getReference("/blocked_users/"+uid1+"/"+uid);
        ref.setValue("blocked");
    }

    public static void getBlockedUsers(String uid, final Presenter pres) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/blocked_users/"+ uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    List<String> ppl = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        String dat = (String) ds.getValue(String.class);
                        if(dat.equals("block")){
                            ppl.add(ds.getKey());
                        }
                    }
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_BLOCKED, ppl);
                    pres.returnData(doo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void sendFeedback(Feedback fb, final Presenter pres) {

        Call<Boolean> m = DbConnector.backendServiceApi.pushFeedback(fb);
        m.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.FEEDBACK_SENT_ACK, true);
                pres.returnData(doo);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[sendFeedback] operation failed "+ t.getMessage() );
                DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.FEEDBACK_SENT_ACK, false);
                pres.returnData(doo);
            }
        });
    }
}
