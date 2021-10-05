package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.ConversationPresenter;
import com.ahmeddebbech.aries_messenger.presenter.MessengerManager;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.util.FlagResolver;
import com.ahmeddebbech.aries_messenger.util.InputChecker;
import com.ahmeddebbech.aries_messenger.util.RandomIdGenerator;
import com.ahmeddebbech.aries_messenger.views.adapters.MessagesListAdapter;
import com.ahmeddebbech.aries_messenger.views.adapters.UserItemAdapter;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversationActivity extends AppCompatActivity implements ContractConversation.View {

    private ConversationPresenter presenter;

    private ImageView back;
    private ImageView photo;
    private TextView displayName;
    private EditText messageField;
    private TextView no_msg_hint;
    private TextView msg_reply_text;
    private ImageButton msg_reply_exit;
    private LinearLayout reply_hlin;
    private ImageButton send;
    private TextView is_typing;
    private User correspondedUser;
    private RecyclerView list_messages;
    private RecyclerView.LayoutManager layoutManager;
    private MessagesListAdapter adapter;
    private ItemTouchHelper.SimpleCallback simpleSwipeCallback;
    private ItemTouchHelper ith;
    private Toolbar toolbar;
    private TextView availability_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        toolbar = (Toolbar) findViewById(R.id.conv_toolbar);
        setSupportActionBar(toolbar);

        setupUi();
        addListeners();
        Intent i = getIntent();
        correspondedUser = new User();
        correspondedUser.setUid(i.getStringExtra("uid"));
        String cv = "";
        if(UserManager.getInstance().getUserModel().getConversations() != null){
            cv = UserManager.getInstance().getUserModel().getConversations().get(correspondedUser.getUid());
        }
        presenter = new ConversationPresenter(this, cv);

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
        msg_reply_exit = findViewById(R.id.msg_reply_exit);
        msg_reply_text = findViewById(R.id.msg_reply_text);
        reply_hlin = findViewById(R.id.msg_reply_hlin);
        is_typing = findViewById(R.id.is_typing_user);
        availability_status = findViewById(R.id.availability_status);
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
                reply_hlin.setVisibility(View.GONE);
                no_msg_hint.setVisibility(View.INVISIBLE);
            }
        });
        this.displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ConversationActivity.this, ContactProfile.class);
                in.putExtra("uid", correspondedUser.getUid());
                in.putExtra("username", correspondedUser.getUsername());
                ConversationActivity.this.startActivity(in);
            }
        });
        this.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ConversationActivity.this, ContactProfile.class);
                in.putExtra("uid", correspondedUser.getUid());
                in.putExtra("username", correspondedUser.getUsername());
                ConversationActivity.this.startActivity(in);
            }
        });
        messageField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_messages.post(new Runnable() {
                    @Override
                    public void run() {
                        if(adapter.getItemCount() > 0) {
                            list_messages.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    }
                });
            }
        });
        simpleSwipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                MessengerManager.getInstance().setMsgToReplyTo(adapter.getRaw(viewHolder.getAdapterPosition()));
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        System.out.println("pos : " + viewHolder.getAdapterPosition());
                        msg_reply_text.setText(MessengerManager.getInstance().getMsgToReplyTo().getContent());
                        reply_hlin.setVisibility(View.VISIBLE);
                        break;
                    case ItemTouchHelper.RIGHT:
                        System.out.println("pos : " + viewHolder.getAdapterPosition());
                        msg_reply_text.setText(MessengerManager.getInstance().getMsgToReplyTo().getContent());
                        reply_hlin.setVisibility(View.VISIBLE);
                        break;
                }
            }

        };
        ith = new ItemTouchHelper(simpleSwipeCallback);
        ith.attachToRecyclerView(list_messages);
        msg_reply_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reply_hlin.setVisibility(View.GONE);
            }
        });
        messageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")) {
                    presenter.sendTypingSignal(true);
                }else{
                    presenter.sendTypingSignal(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.converstation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.conv_more:
                presenter.blockConnection(correspondedUser.getUid());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new MessagesListAdapter(new ArrayList<Message>(), correspondedUser);
        list_messages.setAdapter(adapter);
        presenter.loadUser(correspondedUser.getUid());
        presenter.getConversation(UserManager.getInstance().getUserModel().getUid(), correspondedUser.getUid());
        presenter.trackNewMessages(correspondedUser.getUid());
        presenter.trackIsTypingStatus(correspondedUser.getUid());
    }

    @Override
    public void updateMessage(Message m) {
        adapter.updateMessage(m);
        adapter.notifyItemChanged(adapter.indexOfMessage(m));
    }

    @Override
    public void pushMessageToScreen(Message m) {
        adapter.addMessage(m);
        list_messages.post(new Runnable() {
            @Override
            public void run() {
                list_messages.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }

    @Override
    public void showUserData(User u) {
        correspondedUser = u;
        adapter.setCorr(correspondedUser);

        if(u.getPhotoURL() != null) {
            Picasso.get().load(u.getPhotoURL()).into(photo);
        }
        displayName.setText(u.getDisplayName());
        if(u.getAvailability() == 1) {
            availability_status.setTextColor(getResources().getColor(R.color.online));
        }else{
            availability_status.setTextColor(getResources().getColor(R.color.offline));
        }
        availability_status.setText(FlagResolver.toAvailabilityStatusText(this, u.getAvailability()));
    }
    @Override
    public void showHint(Boolean res) {
        if(res.equals(false)){
            no_msg_hint.setVisibility(View.VISIBLE);
        }else{
            no_msg_hint.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void loadMessages(List<Message> list) {
        no_msg_hint.setVisibility(View.INVISIBLE);
        adapter.setList(null);
        adapter.setList(list);
        list_messages.setAdapter(adapter);
        list_messages.post(new Runnable() {
            @Override
            public void run() {
                list_messages.scrollToPosition(adapter.getItemCount());
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
    public void showTypingLabel(String name) {
        is_typing.setVisibility(View.VISIBLE);
        is_typing.setText(name + " " +getString(R.string.whos_typing));
    }

    @Override
    public void addNewMessage(Message m) {
        no_msg_hint.setVisibility(View.INVISIBLE);
        if(!adapter.getList().contains(m)) {
            adapter.getList().add(m);
        }
        adapter.notifyItemInserted(adapter.getItemCount());
        list_messages.post(new Runnable() {
            @Override
            public void run() {
                list_messages.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }

    @Override
    public void hideTypingLabel() {
        is_typing.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.closeConversation();
        presenter = null;
    }

}