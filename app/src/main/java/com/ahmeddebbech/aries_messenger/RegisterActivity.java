package com.ahmeddebbech.aries_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ahmeddebbech.aries_messenger.database.Database;


public class RegisterActivity extends AppCompatActivity {
     LoggedInUser liu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        String name = i.getStringExtra("display_name");
        TextView t = (TextView)findViewById(R.id.displayNameText);
        t.setText(name);
        liu = i.getParcelableExtra("logged_user");
    }

    public void onProceedClicked(View v){
        TextView t1 = findViewById(R.id.displayNameText);
        TextView t2 = findViewById(R.id.usernameText);
        liu.getUsr().setDisplayName(t1.getText().toString());
        liu.getUsr().setUsername(t2.getText().toString());
        Database.connectToRegister(liu);
    }
}