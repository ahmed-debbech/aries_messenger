package com.ahmeddebbech.aries_messenger.views.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactsGridAdapter extends RecyclerView.Adapter<ContactsGridAdapter.ContactViewHolder> {
    private List<ItemUser> list;
    private static AppCompatActivity activity;

    public ContactsGridAdapter(List<ItemUser> l){
        this.list = l;
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private ImageView iv;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.ct_username);
            iv = itemView.findViewById(R.id.ct_photo);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
