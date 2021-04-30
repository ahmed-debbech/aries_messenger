package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Snapshot;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
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
                    DbConversations.convertToMeta(convId, pres);
                }else {
                    Log.d("error", "Conversation doesn't exist");
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
        DatabaseReferences.REF_CONV_META = database.getReference("/Conversations/Conversations_meta/").child(convId);
        DatabaseReferences.LIS_CONV_META = new ValueEventListener() {
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
        };
        DatabaseReferences.REF_CONV_META.addValueEventListener(DatabaseReferences.LIS_CONV_META);
    }
    private static void getMembers(final Conversation con, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReferences.REF_CONV_MEMBERS = database.getReference("/Conversations/Conversations_members/").child(con.getId());
        DatabaseReferences.LIS_CONV_MEMBERS = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> members = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        members.add(ds.getKey());
                    }
                    con.setMembers(members);
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_CONV, con);
                    pres.returnData(doo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","could not convert to meta conversation");
            }
        };
        DatabaseReferences.REF_CONV_MEMBERS.addValueEventListener(DatabaseReferences.LIS_CONV_MEMBERS);
    }
    public static void getMessages(String conv_id, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReferences.REF_MSGS = database.getReference("/Conversations/Conversations_data/").child(conv_id);
        DatabaseReferences.LIS_MSGS = new ValueEventListener() {
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
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_MESSAGES, list);
                    pres.returnData(doo);
                }else {
                    pres.returnData(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","could not convert to meta conversation");
            }
        };
        DatabaseReferences.REF_MSGS.addValueEventListener(DatabaseReferences.LIS_MSGS);
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
            public void returnData(DatabaseOutput obj) {
                if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.CHECK_CONV_EXISTS) {
                    Boolean bb = (Boolean) obj.getObj();
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
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_LAST_SEEN, index);
                    pres.returnData(doo);
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
    public static void checkNewMessages(String uid, final Presenter pres){
        List<String> convslist = UserManager.getInstance().getAllConvsIds();
        for(int i=0; i<= convslist.size()-1; i++){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReferences.REF_ALL_CONVS[i] = database.getReference("/Conversations/Conversations_data/"+ convslist.get(i));
            DatabaseReferences.LIS_ALL_CONVS[i] = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.CHECK_NEW_MESSAGES_KEY, true);
                        pres.returnData(doo);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error","could not convert to meta conversation");
                }
            };
            DatabaseReferences.REF_ALL_CONVS[i].addValueEventListener(DatabaseReferences.LIS_ALL_CONVS[i]);
        }
    }
    public static void getConversationsIds(String uid, final Presenter pres) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_conversations/"+uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> list = new ArrayList<>();
                    for(DataSnapshot sp : snapshot.getChildren()) {
                        list.add(sp.getValue(String.class));
                    }
                    DatabaseOutput dot = new DatabaseOutput(DatabaseOutputKeys.CONVS_IDS_GETTER, list);
                    pres.returnData(dot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","could not get conversations ids");
            }
        });
    }
}
