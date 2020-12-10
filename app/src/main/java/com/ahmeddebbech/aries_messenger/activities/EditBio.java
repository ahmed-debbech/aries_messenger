package com.ahmeddebbech.aries_messenger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.database.Database;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.InputFieldChecker;

public class EditBio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);

        TextView tv = findViewById(R.id.edit_bio_input);
        tv.addTextChangedListener(new TextWatcher() {

            TextView counter = findViewById(R.id.edit_bio_counter);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int sum = 140 - s.toString().length();
                counter.setText(String.valueOf(sum));
                if(sum < 0) {
                    counter.setTextColor(Color.RED);
                }else{
                    counter.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btn = findViewById(R.id.edit_change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = findViewById(R.id.edit_bio_input);
                if(InputFieldChecker.isLonger(tv.getText().toString(),140)){
                    tv.setError("New bio is too long!");
                }else{
                    if(tv.getText().length() == 0){
                        tv.setError("Please enter a bio to change it.");
                    }else{
                        User.getInstance().setBio(tv.getText().toString());
                        Database.modifyUser(User.getInstance());
                        Toast toast = Toast.makeText(getApplicationContext(), "Bio updated successfully!", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    }
                }
            }
        });
    }
}