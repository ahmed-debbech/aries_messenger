package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.database.DatabaseReferences;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.ConversationPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.adapters.MessagesListAdapter;
import com.ahmeddebbech.aries_messenger.views.adapters.UserItemAdapter;
import com.google.firebase.database.DatabaseReference;
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
    private ImageButton send;
    private User correspondedUser;
    private RecyclerView list_messages;
    private RecyclerView.LayoutManager layoutManager;
    private MessagesListAdapter adapter;
    private ItemTouchHelper.SimpleCallback simpleSwipeCallback;
    private ItemTouchHelper ith;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setupUi();
        addListeners();
        presenter = new ConversationPresenter(this);
        Intent i = getIntent();
        correspondedUser = new User();
        correspondedUser.setUid(i.getStringExtra("uid"));
        presenter.loadData(correspondedUser.getUid());
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
                presenter.sendMessage(messageField.getText().toString(), correspondedUser.getUid());
            }
        });
        messageField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_messages.post(new Runnable() {
                    @Override
                    public void run() {
                        // Call smooth scroll
                        list_messages.smoothScrollToPosition(adapter.getItemCount() - 1);
                    }
                });
            }
        });
        final MessagesListAdapter rec = this.adapter;
        simpleSwipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        rec.notifyItemRemoved(position);
                        break;
                    case ItemTouchHelper.RIGHT:

                        break;
                }
            }
        };
        ith = new ItemTouchHelper(simpleSwipeCallback);
        ith.attachToRecyclerView(list_messages);
    }

    @Override
    public void retContactData(User u) {
        correspondedUser = u;
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
            presenter.getConversationMetadata(UserManager.getInstance().getUserModel().getUid(), correspondedUser.getUid());
        }
    }

    @Override
    public void loadMessages(List<Message> list) {
        no_msg_hint.setVisibility(View.INVISIBLE);
        adapter = new MessagesListAdapter(list, correspondedUser);
        list_messages.setAdapter(adapter);
        list_messages.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                list_messages.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }

    @Override
    public void showError(String error) {
        messageField.setError(error);
    }

    @Override
    public void clearField() {
        messageField.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseReferences.removeConvListener();
    }
}