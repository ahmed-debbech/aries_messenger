package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractContactProfile;
import com.ahmeddebbech.aries_messenger.presenter.ContactProfilePresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactProfile extends AppCompatActivity implements ContractContactProfile.View {
    private String username;
    private String uid;
    private ImageView photo;
    private TextView disp;
    private TextView connections;
    private TextView bio;
    private Button add;

    private ContactProfilePresenter pres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);

        pres = new ContactProfilePresenter(this);

        Intent in = getIntent();
        this.username = in.getStringExtra("username");
        this.uid = in.getStringExtra("uid");
        getSupportActionBar().setTitle(username);

        setupUi();
        addClicks();
    }
    public void setupUi(){
        photo = findViewById(R.id.conta_prof_img);
        disp = findViewById(R.id.conta_prof_disp);
        connections = findViewById(R.id.conta_prof_numconn);
        bio = findViewById(R.id.conta_prof_bio);
        add = findViewById(R.id.conta_prof_add);
        if(UserManager.getInstance().searchForConnection(uid, UserManager.CONNECTED)){
            add.setBackgroundColor(this.getResources().getColor(R.color.disabled_button));
            add.setText(R.string.remove_button);
        }else{
            add.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
            add.setText(R.string.add_button);
        }
        pres.fillUiWithData(uid);
    }
    public void addClicks(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.setText(R.string.wait_label);
                add.setBackgroundColor(Color.WHITE);
                if(UserManager.getInstance().searchForConnection(uid, UserManager.CONNECTED)) {
                    pres.removeFromContact(uid);
                }else{
                    pres.addToContact(uid);
                }
            }
        });
    }
    @Override
    public void loadData(String disp, String photo, String bio) {
        this.disp.setText(disp);
        this.bio.setText(bio);
        Picasso.get().load(photo).into(this.photo);
    }

    @Override
    public void updateUi() {
        if(UserManager.getInstance().searchForConnection(uid, UserManager.CONNECTED)) {
            add.setBackgroundColor(this.getResources().getColor(R.color.disabled_button));
            add.setText(R.string.remove_button);
        }else{
            add.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
            add.setText(R.string.add_button);
        }
    }
}