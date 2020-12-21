package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;
import android.widget.EditText;

import com.ahmeddebbech.aries_messenger.presenter.LoginPresenter;
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

public class DbUtil {


    public static void userExistsInSignIn(final LoginPresenter loginPresenter){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean founder = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(User.class).getEmail().equals(UserManager.getInstance().getUserModel().getEmail())) {
                        Log.d("LOGIN: ", "found");
                        UserManager.getInstance().updateWithCopy(ds.getValue(User.class)); //set model
                        founder = true;
                    }
                }
                loginPresenter.redirectTo(founder);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                founder = false;
            }
        });
    }
    public static void usernameExists(final String username, final RegisterPresenter pres){
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
                    pres.pushErrorToView("This username is taken!");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
