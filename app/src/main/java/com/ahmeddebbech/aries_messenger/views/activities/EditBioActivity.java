package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractBioEdit;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.EditBioPresenter;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

import org.w3c.dom.Text;

public class EditBioActivity extends AppCompatActivity implements ContractBioEdit.View {

    private EditBioPresenter presenter;
    private TextView counter;
    private TextView bio;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        presenter = new EditBioPresenter(this);

        setupUi();
        addListeners();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setupUi(){
        counter = findViewById(R.id.edit_bio_counter);
        bio = findViewById(R.id.edit_bio_input);
        Intent i = getIntent();
        String curBio = (String) getIntent().getStringExtra("bio");
        System.out.println(curBio);
        bio.setText(curBio);
    }
    public void addListeners(){
        Button btn = findViewById(R.id.edit_change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = findViewById(R.id.edit_bio_input);
                if(presenter.inputIsFine(bio.getText().toString()) == true){
                    presenter.updateModel(bio.getText().toString());
                    presenter.modifyUserInDB();
                    Toast toast = Toast.makeText(getApplicationContext(), "Bio updated successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }
        });
        bio.addTextChangedListener(new TextWatcher() {

            TextView counter = findViewById(R.id.edit_bio_counter);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.controlInputBio(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public void updateBioCharCount(String count) {
        counter.setText(count);
    }

    @Override
    public void setTextColor(int color) {
        counter.setTextColor(color);
    }

    @Override
    public void setError(String err) {
        bio.setError(err);
    }
}