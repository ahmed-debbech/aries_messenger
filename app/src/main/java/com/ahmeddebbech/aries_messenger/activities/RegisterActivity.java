package com.ahmeddebbech.aries_messenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.database.Database;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.ahmeddebbech.aries_messenger.util.InputFieldChecker;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
     private EditText username;
     private EditText DisplayName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        username  = (EditText)findViewById(R.id.usernameText);
        DisplayName = (EditText)findViewById(R.id.displayNameText);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        String name = i.getStringExtra("display_name");
        TextView t = (TextView)findViewById(R.id.displayNameText);
        t.setText(name);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!InputFieldChecker.noSpaces(s.toString())){
                    username.setError("Username must not contain spaces!");
                }else{
                    if(InputFieldChecker.isLonger(s.toString(), 24)){
                        username.setError("Your username is too long!");
                    }else{
                        if(!InputFieldChecker.startsWithAlt(s.toString())){
                            username.setError("You must use '@' at the beginning.");
                        }else{
                            if(!InputFieldChecker.usesOnlyAllowedChars(s.toString(), new char[]{'-','_','.'})){
                                username.setError("Please use [A..Z], [a..z], [0..9], ['-','_','.'] only.");
                            }else{
                                //check if username exists
                                Database.usernameExists(s.toString(), username);
                            }
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
        });
        DisplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(InputFieldChecker.isLonger(s.toString(), 32)){
                    DisplayName.setError("Your Display Name is too long!");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void onProceedClicked(View v){
        TextView t1 = findViewById(R.id.displayNameText);
        TextView t2 = findViewById(R.id.usernameText);
        boolean fine = true;
        if(t1.getText().toString().equals("")){
            t1.setError("This field should not be empty!");
            fine =false;
        }
        if(t2.getText().toString().equals("")){
            t2.setError("This field should not be empty!");
            fine = false;
        }
        if(fine) {
            LoggedInUser.getInstance().getUserModel().setDisplayName(t1.getText().toString().trim());
            LoggedInUser.getInstance().getUserModel().setUsername(t2.getText().toString());
            Database.connectToRegister(LoggedInUser.getInstance().getUserModel());
            Toast toast = Toast.makeText(this, "Account created! Please login to your brand new account.", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        username = null;
        DisplayName = null;
    }
}