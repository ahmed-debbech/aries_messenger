package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Snapshot;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.util.RandomIdGenerator;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;

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
    public static void getMessages(String conv_id, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_data/").child(conv_id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Message> list = new ArrayList<>();
                    for(DataSnapshot sp : snapshot.getChildren()){
                        Message m = sp.getValue(Message.class);
                        list.add(m);
                    }
                    Collections.sort(list, new Comparator<Message>() {
                        @Override
                        public int compare(Message o1, Message o2) {
                            return o1.getIndex() - o2.getIndex();
                        }
                    });
                    pres.returnData(list);
                }else {
                    pres.returnData(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","could not convert to meta conversation");
            }
        });
    }
    public static void createConversation(Conversation cv, Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_meta/" + cv.getId() + "/count");
        ref.setValue(cv.getCount());
        ref = database.getReference("/Conversations/Conversations_meta/" + cv.getId() + "/id");
        ref.setValue(cv.getId());
        ref = database.getReference("/Conversations/Conversations_meta/" + cv.getId() + "/latest_msg");
        ref.setValue(cv.getLatest_msg());
        for(String uid : cv.getMembers()) {
            ref = database.getReference("/Conversations/Conversations_members/"+ cv.getId() + "/" + uid + "/seen_index");
            ref.setValue(0);
        }
        ref = database.getReference("/Users_conversations/"+cv.getMembers().get(0)+"/"+cv.getMembers().get(1));
        ref.setValue(cv.getId());
        ref = database.getReference("/Users_conversations/"+cv.getMembers().get(1)+"/"+cv.getMembers().get(0));
        ref.setValue(cv.getId());
        convertToMeta(cv.getId(), pres);
    }
    public static void sendMessage(final String convId, final Message msg){
        DbUtil.checkConvExists(convId, new Presenter() {
            @Override
            public void returnData(Object obj) {
                if(obj instanceof Boolean) {
                    Boolean bb = (Boolean) obj;
                    if (bb == false) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("/Conversations/Conversations_data/" + convId + "/" + msg.getId() + "/");
                        ref.setValue(msg);
                        incrementConvCount(convId, msg.getId());
                        incrementContactSeenIndex(convId, msg.getSender_uid());
                    }
                }
            }
        });
    }

    private static void incrementContactSeenIndex(String convId, String sender_uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_members/" + convId + "/" + sender_uid +"/seen_index");
        ref.setValue(UserManager.getInstance().getCurrentConv().getCount()+1);
    }

    private static void incrementConvCount(String convId, final String msgid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_meta/"+convId+"/count");
        int x = UserManager.getInstance().getCurrentConv().getCount() + 1;
        ref.setValue(x);
        ref = database.getReference("/Conversations/Conversations_meta/"+convId+"/latest_msg");
        ref.setValue(msgid);
    }

    public static void getLastSeenIndex(String uid, String convid, final Presenter pres) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_members/"+convid+"/"+uid+"/seen_index");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer index = snapshot.getValue(Integer.class);
                    pres.returnData(index);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","could not convert to meta conversation");
            }
        });
    }

    public static void sendListOfMessages(List<Message> list, String convid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for(Message m : list) {
            DatabaseReference ref = database.getReference("/Conversations/Conversations_data/" + convid + "/" + m.getId());
            ref.setValue(m);
        }
        DatabaseReference ref = database.getReference("/Conversations/Conversations_members/"+convid+"/"+UserManager.getInstance().getUserModel().getUid() + "/seen_index");
        ref.setValue(UserManager.getInstance().getCurrentConv().getCount());
    }
}
