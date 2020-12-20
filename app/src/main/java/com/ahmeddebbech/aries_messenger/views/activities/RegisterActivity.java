package com.ahmeddebbech.aries_messenger.views.activities;

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
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbUtil;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

public class RegisterActivity extends AppCompatActivity {
     private EditText username;
     private EditText DisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        username  = (EditText)findViewById(R.id.register_username);
        DisplayName = (EditText)findViewById(R.id.register_disp_name);

        setSupportActionBar(toolbar);

        TextView t = (TextView)findViewById(R.id.register_disp_name);
        t.setText(User.getInstance().getDisplayName());
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!InputChecker.noSpaces(s.toString())){
                    username.setError("Username must not contain spaces!");
                }else{
                    if(InputChecker.isLonger(s.toString(), 24)){
                        username.setError("Your username is too long!");
                    }else{
                        if(!InputChecker.startsWithAlt(s.toString())){
                            username.setError("You must use '@' at the beginning.");
                        }else{
                            if(!InputChecker.usesOnlyAllowedChars(s.toString(), new char[]{'-','_','.'})){
                                username.setError("Please use [A..Z], [a..z], [0..9], ['-','_','.'] only.");
                            }else{
                                //check if username exists
                                DbUtil.usernameExists(s.toString(), username);
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
                if(InputChecker.isLonger(s.toString(), 32)){
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
            User.getInstance().setDisplayName(t1.getText().toString().trim());
            User.getInstance().setUsername(t2.getText().toString());

            DbConnector.connectToRegister(User.getInstance());

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