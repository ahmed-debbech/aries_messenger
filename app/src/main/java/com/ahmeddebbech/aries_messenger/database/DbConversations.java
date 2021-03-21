package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbConversations {

    public static void getConversations(String uid, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_conversations/").child(uid);
        Log.d("tagg --", "jkk");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> convs = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        convs.add(ds.getKey());
                    }
                    if(convs.size() == 0){
                        convs = null;
                        pres.returnData(convs);
                    }else{
                        //if the list in users_Conversations is not empty
                        DbConversations.convertIdToConversations(convs, pres);
                    }
                }
                pres.returnData(null);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","*%*$#%(#$%*#@$%(");
            }
        });
    }
    private static void convertIdToConversations(List<String> list, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = null;
        final List<Conversation> convs = new ArrayList<>();
        for(String id : list) {
            ref = database.getReference("/Conversations_meta/").child(id);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Conversation c;
                        c = snapshot.getValue(Conversation.class);
                        convs.add(c);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error","could not convert ids to Conversations");
                }
            });
        }
        pres.returnData(convs);
    }
}
