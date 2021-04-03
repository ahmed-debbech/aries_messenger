package com.ahmeddebbech.aries_messenger.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder> {
    private List<Message> list;

    public MessagesListAdapter(List<Message> list){
        this.list = list;
    }
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView status;
        private TextView date;
        private TextView content;
        private CardView background;
        private Message ref;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.msg_status);
            date = itemView.findViewById(R.id.msg_date);
            content = itemView.findViewById(R.id.msg_content);
            background = itemView.findViewById(R.id.msg_card);

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
        holder.status.setText(m.getStatus());
        holder.date.setText("" + m.getDate());
        holder.ref = m;
        if (holder.ref.getSender_uid().equals(UserManager.getInstance().getUserModel().getUid())) {
            holder.background.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.sender_message));
        }else{
            holder.background.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.receiver_message));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
