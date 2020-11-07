package com.ahmeddebbech.aries_messenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.database.Database;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.ahmeddebbech.aries_messenger.util.InputFieldChecker;


public class RegisterActivity extends AppCompatActivity {
     LoggedInUser liu;
     EditText username;
     EditText DisplayName;
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
        liu = i.getParcelableExtra("logged_user");
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InputFieldChecker ifc = new InputFieldChecker();
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
        liu.getUserModel().setDisplayName(t1.getText().toString());
        liu.getUserModel().setUsername(t2.getText().toString());
        Database.connectToRegister(liu.getUserModel());
    }
}