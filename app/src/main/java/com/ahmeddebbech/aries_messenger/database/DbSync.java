package com.ahmeddebbech.aries_messenger.database;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DbSync {

    /*public static void trackUserExistence(final LoggedInUser log, final MainActivity ma){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        ref.orderByChild("uid").equalTo(LoggedInUser.getInstance().getUserModel().getUid()).addValueEventListener(new ValueEventListener() {
            boolean founder = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    founder = true;
                }
                if (!founder) {
                    LoggedInUser.getInstance().signOut();
                    Intent intent = new Intent(ma, LoginActivity.class);
                    ma.startActivity(intent);
                }else{
                    ProgressBar pb = ma.findViewById(R.id.wait_loop);
                    pb.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                founder = false;
            }
        });
    }*/
    public static void syncUserConnections(String uid, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users_connections/"+uid);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Map<String, String> connections = new HashMap<>();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        connections.put(ds.getKey(), ds.getValue(String.class));
                    }
                    if(connections.size() == 0){
                        connections = null;
                    }
                    pres.returnData(connections);
                }
                pres.returnData(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
