package com.ahmeddebbech.aries_messenger.views.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.MessengerManager;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.util.AriesCalendar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder>{
    private List<Message> list;
    private User corr;
    List<MessageViewHolder> vlist;
    public MessagesListAdapter(List<Message> list, User corr){
        this.list = list;
        this.corr = corr;
        this.vlist = new ArrayList<>();
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView status;
        private TextView date;
        private TextView content;
        private CardView background;
        private ImageView msg_image;
        private ImageView msg_status;
        private ImageView msg_edited_flag;
        private Message ref;
        private RelativeLayout msg_edit_panel;
        private EditText msg_edit;
        private TextView msg_edit_button;
        private TextView msg_cancel_button;
        private ConstraintLayout msg_main_panel;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.msg_status);
            date = itemView.findViewById(R.id.msg_date);
            content = itemView.findViewById(R.id.msg_content);
            background = itemView.findViewById(R.id.msg_card);
            msg_image = itemView.findViewById(R.id.msg_img);
            msg_status = itemView.findViewById(R.id.msg_status);
            msg_edit_panel = itemView.findViewById(R.id.msg_edit_panel);
            msg_edit = itemView.findViewById(R.id.msg_edit);
            msg_main_panel = itemView.findViewById(R.id.msg_main_panel);
            msg_cancel_button = itemView.findViewById(R.id.msg_cancel_button);
            msg_edit_button = itemView.findViewById(R.id.msg_edit_button);
            msg_edited_flag = itemView.findViewById(R.id.msg_edited_flag);
        }

        @Override
        public String toString() {
            return "content : " +content.getText();
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        MessagesListAdapter.MessageViewHolder mvh = new MessagesListAdapter.MessageViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message m = list.get(position);
        //System.out.println("will be added : " + holder.toString());
        holder.content.setText(m.getContent());
        holder.status.setImageDrawable(Message.toDrawable(holder.itemView, m.getStatus()));
        AriesCalendar ac = new AriesCalendar(m.getDate());
        holder.date.setText(ac.toString());
        holder.ref = m;
        if(holder.ref.getIs_edited() == true){
            holder.msg_edited_flag.setVisibility(View.VISIBLE);
        }else{
            holder.msg_edited_flag.setVisibility(View.INVISIBLE);
        }
        if (holder.ref.getSender_uid().equals(UserManager.getInstance().getUserModel().getUid())) {
            holder.background.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.sender_message));
            Picasso.get().load(UserManager.getInstance().getUserModel().getPhotoURL()).into(holder.msg_image);
            holder.msg_status.setVisibility(View.VISIBLE);
            final MessageViewHolder hld = holder;
            holder.background.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    hld.msg_edit.setText(hld.content.getText());
                    hld.msg_main_panel.setVisibility(View.INVISIBLE);
                    hld.msg_edit_panel.setVisibility(View.VISIBLE);
                    return true;
                }
            });
            holder.msg_cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hld.msg_main_panel.setVisibility(View.VISIBLE);
                    hld.msg_edit_panel.setVisibility(View.INVISIBLE);
                }
            });
            holder.msg_edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessengerManager.getInstance().editMessage(hld.ref.getId(), hld.msg_edit.getText().toString());
                }
            });
        }else{
            Picasso.get().load(corr.getPhotoURL()).into(holder.msg_image);
            holder.background.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.receiver_message));
            holder.msg_status.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public MessageViewHolder getItem(int pos){
        System.out.println("all : " + this.vlist.size());
        return this.vlist.get(pos);
    }
    public String swipe(int position){
        return this.list.get(position).getContent();
    }
}
