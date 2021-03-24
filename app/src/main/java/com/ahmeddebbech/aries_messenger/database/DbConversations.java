package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.google.firebase.database.ChildEventListener;
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

public class DbConversations {

    public static void getConversation(String uidA, String uidB, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_conversations/").child(uidA).child(uidB);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String convId = snapshot.getValue(String.class);
                    Log.d("#@e", "convv id " + convId);
                    DbConversations.convertToMeta(convId, pres);
                }else {
                    Log.d("#@e", "convv null id" + snapshot.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","*%*$#%(#$%*#@$%(");
            }
        });
    }
    private static void convertToMeta(String convId, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_meta/").child(convId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer count = snapshot.child("count").getValue(Integer.class);
                    String id = snapshot.child("id").getValue(String.class);
                    String latest_msg = snapshot.child("latest_msg").getValue(String.class);
                    Conversation c = new Conversation();
                    c.setId(id); c.setCount(count); c.setLatest_msg(latest_msg);

                    DbConversations.getMembers(c, pres);

                    Log.d("#@e", "conv id " + id);
                }else {
                    Log.d("#@e", "convv null" + snapshot.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","could not convert to meta conversation");
            }
        });
    }
    private static void getMembers(final Conversation con, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_members/").child(con.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> members = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        members.add(ds.getKey());
                    }
                    con.setMembers(members);
                    pres.returnData(con);
                }else {
                    Log.d("#@e", "snap : " + snapshot.toString());

                    Log.d("#@e", "convv null" + snapshot.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","could not convert to meta conversation");
            }
        });
    }
}
