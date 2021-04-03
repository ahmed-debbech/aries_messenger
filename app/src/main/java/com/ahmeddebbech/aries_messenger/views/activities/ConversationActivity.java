package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.adapters.MessagesListAdapter;
import com.ahmeddebbech.aries_messenger.views.adapters.UserItemAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ConversationActivity extends AppCompatActivity implements ContractConversation.View {

    private ConversationPresenter presenter;

    private Toolbar toolbar;
    private ImageView back;
    private ImageView photo;
    private TextView displayName;
    private EditText messageField;
    private TextView no_msg_hint;
    private Button send;
    private String uidB;
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
        uidB = i.getStringExtra("uid");
        presenter.loadData(uidB);
    }
    public void setupUi(){
        back = findViewById(R.id.conv_return);
        no_msg_hint = findViewById(R.id.no_msg_hint);
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
                presenter.sendMessage(messageField.getText().toString());
            }
        });
    }

    @Override
    public void retContactData(User u) {
        Picasso.get().load(u.getPhotoURL()).into(photo);
        displayName.setText(u.getDisplayName());
        presenter.conversationExists(UserManager.getInstance().getUserModel().getUid());
    }

    @Override
    public void showHint(Boolean res) {
        if(res.equals(false)){
            no_msg_hint.setVisibility(View.VISIBLE);
        }else{
            no_msg_hint.setVisibility(View.INVISIBLE);
            presenter.getConversationMetadata(UserManager.getInstance().getUserModel().getUid(), uidB);
        }
    }

    @Override
    public void loadMessages(List<Message> list) {
        adapter = new MessagesListAdapter(list);
        list_messages.setAdapter(adapter);
    }
}