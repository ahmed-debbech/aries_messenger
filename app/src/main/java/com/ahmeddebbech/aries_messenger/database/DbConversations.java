package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
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
        Log.d("#@e", "totot");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_conversations/").child(uidA);
        Query o1 = ref.orderByChild("id").equalTo(uidB);
        o1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String convId = snapshot.getKey();
                    Log.d("#@e", "convv " + convId);
                    //DbConversations.convertIdToConversations(convId, pres);
                }else {
                    Log.d("#@e", "convv null" + snapshot.getKey());
                }
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
        Log.d("#@e" , list.toString());
        for(String id : list) {
            ref = database.getReference("/Conversations_meta/").child(id);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Conversation c;
                        Log.d("#@e", "done " + snapshot.toString());
                        c = snapshot.getValue(Conversation.class);
                        convs.add(c);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error", "could not convert ids to Conversations");
                }
            });
        }
    }
}
