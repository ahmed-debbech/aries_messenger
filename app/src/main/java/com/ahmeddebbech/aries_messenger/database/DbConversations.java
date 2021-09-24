package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Snapshot;

import com.ahmeddebbech.aries_messenger.database.model.MessagePersist;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.presenter.MessengerManager;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.util.RandomIdGenerator;
import com.ahmeddebbech.aries_messenger.views.activities.MainActivity;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_CONV, null);
                    pres.returnData(doo);
                    Log.d(DatabaseOutputKeys.TAG_DB, "[getConversation] Conversation doesn't exist");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[getConversation] operation cancelled due to "+ error.getCode());
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
                }else {
                    Log.d(DatabaseOutputKeys.TAG_DB, "[convertToMeta] COnversation doesn't exist");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[convertToMeta] operation cancelled due to "+ error.getCode());
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
                Log.d(DatabaseOutputKeys.TAG_DB, "[getMembers] operation cancelled due to "+ error.getCode());
            }
        };
        DatabaseReferences.REF_CONV_MEMBERS.addValueEventListener(DatabaseReferences.LIS_CONV_MEMBERS);
    }
    public static void getMessages(String conv_id, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_data/").child(conv_id);
        ValueEventListener vv = new ValueEventListener() {
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
                Log.d(DatabaseOutputKeys.TAG_DB, "[getMessages] operation cancelled due to "+ error.getCode());
            }
        };
        ref.addListenerForSingleValueEvent(vv);
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
    public static void sendMessage(final String recep_uid, final Message msg, final Conversation cv){

        MessagePersist mper = new MessagePersist(msg, recep_uid, cv);
        Call<Message> m = DbConnector.backendServiceApi.sendMessage(mper);
        m.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.d(DatabaseOutputKeys.TAG_DB, "onResponse: " + response.body() );
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[sendMessage] operation failed "+ t.getMessage() );
            }
        });
    }
    private static void incrementContactSeenIndex(String convId, String sender_uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_members/" + convId + "/" + sender_uid +"/seen_index");
        ref.setValue(MessengerManager.getInstance().getCurrentConv().getCount()+1);
    }
    private static void incrementConvCount(String convId, final String msgid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_meta/"+convId+"/count");
        int x = MessengerManager.getInstance().getCurrentConv().getCount() + 1;
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
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_LAST_SEEN_INDEX, index);
                    pres.returnData(doo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[getLastSeenIndex] operation cancelled due to "+ error.getCode());
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
        ref.setValue(MessengerManager.getInstance().getCurrentConv().getCount());
    }
    public static void getNewMessages(String convid, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReferences.REF_MSGS = database.getReference("/Conversations/Conversations_data/" + convid);
        DatabaseReferences.LIS_MSGS = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Message m = snapshot.getValue(Message.class);
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_NEW_MSG, m);
                    pres.returnData(doo);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Message m = snapshot.getValue(Message.class);
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.GET_CHANGED_MESSAGE, m);
                    pres.returnData(doo);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[getNewMessages] operation cancelled due to " + error.getCode());
            }
        };
        DatabaseReferences.REF_MSGS.addChildEventListener(DatabaseReferences.LIS_MSGS);
    }
    public static void checkNewMessages(String uid, List<String> convslist, final Presenter pres){
        if(convslist != null) {
            for (int i = 0; i <= convslist.size() - 1; i++) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReferences.REF_ALL_CONVS[i] = database.getReference("/Conversations/Conversations_meta/" + convslist.get(i));
                DatabaseReferences.LIS_ALL_CONVS[i] = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String id = snapshot.child("id").getValue(String.class);
                            int count = snapshot.child("count").getValue(Integer.class).intValue();
                            String last_msg = snapshot.child("latest_msg").getValue(String.class);
                            Conversation cv = new Conversation(id, null, last_msg, count);
                            DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.CHECK_NEW_MESSAGES_KEY, cv);
                            pres.returnData(doo);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(DatabaseOutputKeys.TAG_DB, "[checkNewMessages] operation cancelled due to "+ error.getCode());
                    }
                };
                DatabaseReferences.REF_ALL_CONVS[i].addValueEventListener(DatabaseReferences.LIS_ALL_CONVS[i]);
            }
        }
    }
    public static void getConversationsIds(String uid, final Presenter pres) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_conversations/"+uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Map<String, String> map = new HashMap<>();
                    for(DataSnapshot sp : snapshot.getChildren()) {
                        map.put(sp.getKey(), sp.getValue(String.class));
                    }
                    DatabaseOutput dot = new DatabaseOutput(DatabaseOutputKeys.CONVS_IDS_GETTER, map);
                    pres.returnData(dot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[getConversationsIds] operation cancelled due to "+ error.getCode());
            }
        });
    }
    public static void editMsg(String id, String msg_id, String msg_cont) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_data/"+id+"/"+msg_id+"/is_edited");
        ref.setValue(true);
        ref = database.getReference("/Conversations/Conversations_data/"+id+"/"+msg_id+"/content");
        ref.setValue(msg_cont);
    }
    public static void trackWhosTyping( String convid, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReferences.REF_WHOS_TYPING = database.getReference("/Conversations/Conversations_members/" + convid);
        DatabaseReferences.LIS_WHOS_TYPING = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> typing_users = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Log.d("type0-", ds.toString());
                        Log.d("type0-", ds.child("is_typing").toString());
                        if (ds.child("is_typing").exists()) {
                            if (ds.child("is_typing").getValue(String.class).equals("*")) {
                                typing_users.add(ds.getKey());
                            }
                        }
                    }
                    Log.d("type0-", typing_users.toString());
                    DatabaseOutput dot = new DatabaseOutput(DatabaseOutputKeys.GET_TYPERS, typing_users);
                    pres.returnData(dot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[trackWhostyping] operation cancelled due to " + error.getCode());
            }
        };
        DatabaseReferences.REF_WHOS_TYPING.addValueEventListener(DatabaseReferences.LIS_WHOS_TYPING);
    }
    public static void sendTypingSignal(String uid, String convid, boolean signal) {
        if(signal == true) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("/Conversations/Conversations_members/" + convid + "/" + uid + "/is_typing");
            ref.setValue("*");
        }else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("/Conversations/Conversations_members/" + convid + "/" + uid + "/is_typing");
            ref.removeValue();
        }
    }
    public static void updateMessageState(String state, String convid, String msg_id) {
        if(state == Message.SEEN){
            Log.d("xcc", "updateMessageState: " + msg_id + " " + convid);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("/Conversations/Conversations_data/" + convid + "/" + msg_id+ "/status");
            ref.setValue(Message.SEEN);
        }
    }
    public static void getOneMessage(String conv_id, final String msg_id, final Presenter pres) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Conversations/Conversations_data/"+conv_id+"/"+msg_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Message m = snapshot.getValue(Message.class);
                    DatabaseOutput dot = new DatabaseOutput(DatabaseOutputKeys.GET_ONE_MESSAGE, m);
                    pres.returnData(dot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[getOneMessage] operation cancelled due to "+ error.getCode());
            }
        });
    }
}
