package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;
import android.widget.EditText;

import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.presenter.LoginPresenter;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.presenter.RegisterPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.activities.LoginActivity;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DbUtil {


    public static void userExistsInSignIn(final Presenter loginPresenter){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            Boolean founder = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(User.class).getEmail().equals(UserManager.getInstance().getUserModel().getEmail())) {
                        UserManager.getInstance().updateWithCopy(ds.getValue(User.class)); //set model
                        founder = true;
                    }
                }
                DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.USER_EXISTS, founder);
                loginPresenter.returnData(doo);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[userExistsInSignIn] operation cancelled due to " + databaseError.getCode());
                founder = false;
            }
        });
    }
    public static void usernameExists(final String username, final Presenter pres){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean founder = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(User.class).getUsername().equals(username)) {
                        founder = true;
                    }
                }
                if (founder) {
                    DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.USERNAME_EXISTS, "This username is taken!");
                    pres.returnData(doo);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(DatabaseOutputKeys.TAG_DB, "[usernameExists] operation cancelled due to " + databaseError.getCode());
            }
        });
    }
}
