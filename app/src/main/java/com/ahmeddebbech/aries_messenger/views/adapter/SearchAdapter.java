package com.ahmeddebbech.aries_messenger.views.adapter;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractSearchItem;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.SearchItem;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.SearchItemPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.activities.ContactProfile;
import com.ahmeddebbech.aries_messenger.views.activities.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private ArrayList<SearchItem> list;
    private static SearchActivity sa;

    public static class SearchViewHolder extends RecyclerView.ViewHolder implements ContractSearchItem.View {
        private ImageView photo;
        private TextView disp;
        private TextView username;
        private Button addbutton;
        private TextView uid;
        private ContractSearchItem.Presenter pres;

        public SearchViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            disp = itemView.findViewById(R.id.displayName);
            username = itemView.findViewById(R.id.username);
            addbutton = itemView.findViewById(R.id.item_add);
            uid = itemView.findViewById(R.id.uid);

            pres = new SearchItemPresenter(this);
            setClicks();
        }
        public void setClicks(){
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(SearchAdapter.sa, ContactProfile.class);
                    in.putExtra("uid", uid.getText());
                    in.putExtra("username", username.getText());
                    SearchAdapter.sa.startActivity(in);
                }
            });
            disp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(SearchAdapter.sa, ContactProfile.class);
                    in.putExtra("uid", uid.getText());
                    in.putExtra("username", username.getText());
                    SearchAdapter.sa.startActivity(in);
                }
            });
            addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addbutton.setText(R.string.wait_label);
                    addbutton.setBackgroundColor(Color.WHITE);
                    pres.addToContact(uid.getText().toString());
                }
            });
        }
        @Override
        public void updateUi() {
            addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.disabled_button));
            addbutton.setText(R.string.remove_button);
        }
    }
    public SearchAdapter(ArrayList<SearchItem> listOfItems, SearchActivity sa) {
        list = listOfItems;
        this.sa = sa;
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
        holder.uid.setText(currentItem.getUid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
