package com.ahmeddebbech.aries_messenger.activities;

import android.os.Bundle;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.database.Database;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.InputFieldChecker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditDispName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_disp_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = findViewById(R.id.edit_change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = findViewById(R.id.edit_input_dispName);
                if(InputFieldChecker.isLonger(tv.getText().toString(),32)){
                    tv.setError("New display name is too long!");
                }else{
                    if(tv.getText().length() == 0){
                        tv.setError("Please enter your new display name.");
                    }else{
                        User.getInstance().setDisplayName(tv.getText().toString());
                        System.out.println(User.getInstance().getEmail());
                        Database.modifyUser(User.getInstance());
                    }
                }
            }
        });
    }
}