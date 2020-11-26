package com.ahmeddebbech.aries_messenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.database.Database;
import com.ahmeddebbech.aries_messenger.database.DatabaseConnector;
import com.ahmeddebbech.aries_messenger.database.Utilities;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.ahmeddebbech.aries_messenger.util.InputFieldChecker;

public class RegisterActivity extends AppCompatActivity {
     private EditText username;
     private EditText DisplayName;
     User model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        username  = (EditText)findViewById(R.id.register_username);
        DisplayName = (EditText)findViewById(R.id.register_disp_name);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        model = i.getParcelableExtra("user");
        TextView t = (TextView)findViewById(R.id.register_disp_name);
        t.setText(model.getDisplayName());
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
                                Utilities.usernameExists(s.toString(), username);
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
        TextView t1 = findViewById(R.id.register_disp_name);
        TextView t2 = findViewById(R.id.register_username);
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
            model.setDisplayName(t1.getText().toString().trim());
            model.setUsername(t2.getText().toString());
            DatabaseConnector.connectToRegister(model);
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