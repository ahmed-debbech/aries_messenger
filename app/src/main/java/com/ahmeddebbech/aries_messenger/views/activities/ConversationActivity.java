package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.ConversationPresenter;
import com.ahmeddebbech.aries_messenger.views.adapters.MessagesListAdapter;
import com.ahmeddebbech.aries_messenger.views.adapters.UserItemAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity implements ContractConversation.View {

    private ConversationPresenter presenter;

    private Toolbar toolbar;
    private ImageView back;
    private ImageView photo;
    private TextView displayName;
    private EditText messageField;
    private Button send;
    private RecyclerView list_messages;
    private RecyclerView.LayoutManager layoutManager;
    private MessagesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setupUi();
        addListeners();
        presenter = new ConversationPresenter(this);
        Intent i = getIntent();
        String uid = i.getStringExtra("uid");
        presenter.loadData(uid);
    }
    public void setupUi(){
        back = findViewById(R.id.conv_return);
        photo = findViewById(R.id.conv_photo);
        displayName = findViewById(R.id.conv_dispname);
        messageField = findViewById(R.id.conv_message_field);
        send = findViewById(R.id.conv_send);
        list_messages = findViewById(R.id.conv_list_messages);
        layoutManager = new LinearLayoutManager(this);
        list_messages.setLayoutManager(layoutManager);
    }
    public void addListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("entered function");
                /** for testin */
                Message m = new Message("233","494","9ee", "233","494","9ee" , "elele","roroir");
                List<Message> list = new ArrayList();
                list.add(m);
                adapter = new MessagesListAdapter(list);
                list_messages.setAdapter(adapter);
                /** for testing */
            }
        });
    }

    @Override
    public void retContactData(User u) {
        Picasso.get().load(u.getPhotoURL()).into(photo);
        displayName.setText(u.getDisplayName());
    }
}