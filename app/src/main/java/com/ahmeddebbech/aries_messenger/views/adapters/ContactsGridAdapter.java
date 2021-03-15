package com.ahmeddebbech.aries_messenger.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;
import java.util.List;

public class ContactsGridAdapter extends RecyclerView.Adapter<ContactsGridAdapter.ContactViewHolder> {
    private List<ItemUser> list;
    private static AppCompatActivity activity;

    public ContactsGridAdapter(List<ItemUser> l){
        this.list = l;
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            //you init all the views
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
