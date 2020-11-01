package com.ahmeddebbech.aries_messenger.database;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmeddebbech.aries_messenger.LoggedInUser;
import com.ahmeddebbech.aries_messenger.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    Database(){

    }
    public static void connectToSignup(LoggedInUser loggedInUser, LoginActivity la) {
        userExists(loggedInUser);
        //if the user doesn't exist then it adds it to Database internally in userExists method.
        //show a loading interface
    }
    public static void userExists(LoggedInUser loggedInUser){
        FirebaseAuth fa = FirebaseAuth.getInstance();
        final LoggedInUser lg = loggedInUser;
        fa.fetchSignInMethodsForEmail(loggedInUser.getFirebaseUserObject().getEmail())
            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>(){
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(task.getResult().getSignInMethods().isEmpty()){
                    Log.d("#####", "found");
                }else{
                    Database.addUserToDatabase(lg);
                }
            }
        });
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
