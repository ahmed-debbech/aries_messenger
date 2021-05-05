package com.ahmeddebbech.aries_messenger.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.util.AriesCalendar;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder> {
    private List<Message> list;
    private User corr;

    public MessagesListAdapter(List<Message> list, User corr){
        this.list = list;
        this.corr = corr;
    }
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView status;
        private TextView date;
        private TextView content;
        private CardView background;
        private ImageView msg_image;
        private ImageView msg_status;
        private Message ref;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.msg_status);
            date = itemView.findViewById(R.id.msg_date);
            content = itemView.findViewById(R.id.msg_content);
            background = itemView.findViewById(R.id.msg_card);
            msg_image = itemView.findViewById(R.id.msg_img);
            msg_status = itemView.findViewById(R.id.msg_status);
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
        holder.content.setText(m.getContent());
        holder.status.setImageDrawable(Message.toDrawable(holder.itemView, m.getStatus()));
        AriesCalendar ac = new AriesCalendar(m.getDate());
        holder.date.setText(ac.toString());
        holder.ref = m;
        if (holder.ref.getSender_uid().equals(UserManager.getInstance().getUserModel().getUid())) {
            holder.background.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.sender_message));
            Picasso.get().load(UserManager.getInstance().getUserModel().getPhotoURL()).into(holder.msg_image);
            holder.msg_status.setVisibility(View.VISIBLE);
            final MessageViewHolder hld = holder;
            holder.background.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    hld.content.setText("Pressed!");
                    return true;
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

}
