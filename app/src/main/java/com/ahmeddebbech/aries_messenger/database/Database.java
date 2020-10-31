package com.ahmeddebbech.aries_messenger.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    Database(){

    }
    public static void connectToSignup(LoggedInUser loggedInUser){
        Database.userExists(loggedInUser);
        /*if(Database.userExists(loggedInUser)){
            return;
        }else {
            Database.addUserToDatabase(loggedInUser);
        }*/
    }
    public static boolean userExists(LoggedInUser loggedInUser){
        FirebaseAuth fa = FirebaseAuth.getInstance();
        fa.fetchSignInMethodsForEmail(loggedInUser.getFirebaseUserObject().getEmail())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if(!task.getResult().getSignInMethods().isEmpty()){
                            Log.d("#####", "found");
                        }else{
                            Log.d("####", "not found");
                        }
                    }
                });
        return true;
    }
    public static void addUserToDatabase(LoggedInUser loggedInUser){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Uid
        DatabaseReference refUid = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/uid");
        refUid.setValue(loggedInUser.getFirebaseUserObject().getUid());
        //username
        DatabaseReference refUsername = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/username");
        refUsername.setValue("@xxxx");
        //display name
        DatabaseReference refDisplayName = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/display-name");
        refDisplayName.setValue(loggedInUser.getFirebaseUserObject().getDisplayName());
        //email
        DatabaseReference refEmail = FirebaseDatabase.getInstance().getReference("/Users/"+loggedInUser.getFirebaseUserObject().getUid()+"/email");
        refEmail.setValue(loggedInUser.getFirebaseUserObject().getEmail());
    }
}
