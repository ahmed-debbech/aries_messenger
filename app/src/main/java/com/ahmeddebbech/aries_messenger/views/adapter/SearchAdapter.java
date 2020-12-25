package com.ahmeddebbech.aries_messenger.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.model.SearchItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private ArrayList<SearchItem> list;

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;
        public TextView disp;
        public TextView username;
        public Button addbutton;

        public SearchViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            disp = itemView.findViewById(R.id.displayName);
            username = itemView.findViewById(R.id.username);
            addbutton = itemView.findViewById(R.id.item_add);
        }
    }
    public SearchAdapter(ArrayList<SearchItem> listOfItems) {
        list = listOfItems;
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        SearchViewHolder svh = new SearchViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        SearchItem currentItem = list.get(position);
        Picasso.get().load(currentItem.getPhoto()).into(holder.photo);
        holder.disp.setText(currentItem.getDisplayName());
        holder.username.setText(currentItem.getUsername());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
