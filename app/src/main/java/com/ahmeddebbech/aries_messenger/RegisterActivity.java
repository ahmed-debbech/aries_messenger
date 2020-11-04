package com.ahmeddebbech.aries_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ahmeddebbech.aries_messenger.database.Database;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;

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
        Bundle bundle = getIntent().getExtras();
        liu = bundle.getParcelable("com.ahmeddebbech.aries_messenger.LoggedInUser");
    }

    public void onProceedClicked(View v){
        Log.d("###ERROR####", "-1000000");
        liu.getUsr().setDisplayName(((TextView)findViewById(R.id.displayNameText)).getText().toString());
        liu.getUsr().setUsername(((TextView)findViewById(R.id.usernameText)).getText().toString());
        Log.d("###ERROR####", "000000");
        Database.connectToRegister(liu);
        Log.d("###ERROR####", "1111111");
    }
}