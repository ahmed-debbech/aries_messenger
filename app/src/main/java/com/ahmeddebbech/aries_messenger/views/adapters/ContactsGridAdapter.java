package com.ahmeddebbech.aries_messenger.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.views.activities.ConversationActivity;
import com.ahmeddebbech.aries_messenger.views.activities.LoginActivity;
import com.ahmeddebbech.aries_messenger.views.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactsGridAdapter extends RecyclerView.Adapter<ContactsGridAdapter.ContactViewHolder> {
    private List<ItemUser> list;
    private static Context parent_activity;

    public ContactsGridAdapter(List<ItemUser> l, Context act){
        parent_activity= act;
        this.list = l;
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private ImageView iv;
        private CardView cv;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.ct_username);
            iv = itemView.findViewById(R.id.ct_photo);
            cv = itemView.findViewById(R.id.card);
        }
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // i create the viewholder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        ContactsGridAdapter.ContactViewHolder mvh = new ContactsGridAdapter.ContactViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        //associate the data
        ItemUser i = list.get(position);
        holder.username.setText(i.getUsername());
        Picasso.get().load(i.getPhoto()).into(holder.iv);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(parent_activity, ConversationActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                parent_activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
