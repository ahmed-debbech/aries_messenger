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
    public static void syncUserConnections(String uid, Presenter pres){
        DbBasic.getConnections(uid, pres);
    }
}
