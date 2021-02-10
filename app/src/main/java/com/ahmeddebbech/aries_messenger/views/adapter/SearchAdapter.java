package com.ahmeddebbech.aries_messenger.views.adapter;

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
import com.ahmeddebbech.aries_messenger.contracts.ContractItemList;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.SearchItemPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.activities.ContactProfile;
import com.ahmeddebbech.aries_messenger.views.activities.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private ArrayList<ItemUser> list;
    private static SearchActivity sa;

    public static class SearchViewHolder extends RecyclerView.ViewHolder implements ContractItemList.View {
        private ImageView photo;
        private TextView disp;
        private TextView username;
        private Button addbutton;
        private TextView uid;
        private ItemUser refToModel;
        private ContractItemList.Presenter pres;

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
                    if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.CONNECTED)){
                        pres.removeFromContact(uid.getText().toString());
                    }else{
                        if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.PENDING)){
                            pres.removeFromContact(uid.getText().toString());
                        }else{
                            if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.WAITING)){
                                pres.removeFromContact(uid.getText().toString());
                            }else{
                                pres.addToContact(uid.getText().toString());
                            }
                        }
                    }
                }
            });
        }
        @Override
        public void updateUi() {
            if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.CONNECTED)) {
                addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.disabled_button));
                addbutton.setText(R.string.remove_button);
            }else {
                if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.PENDING)){
                    addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.colorPrimary));
                    addbutton.setText(R.string.pending_button);
                }else{
                    if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.WAITING)){
                        addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.disabled_button));
                        addbutton.setText(R.string.waiting_button);
                    }else{
                        addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.colorPrimary));
                        addbutton.setText(R.string.add_button);
                    }
                }
            }
        }
    }
    public SearchAdapter(ArrayList<ItemUser> listOfItems, SearchActivity sa) {
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
        ItemUser currentItem = list.get(position);
        Picasso.get().load(currentItem.getPhoto()).into(holder.photo);
        holder.disp.setText(currentItem.getDisplayName());
        holder.username.setText(currentItem.getUsername());
        holder.uid.setText(currentItem.getUid());
        holder.refToModel = currentItem;
        //check how the button should be showed base on the existance of the connection
        if(UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.CONNECTED)){
            holder.addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.disabled_button));
            holder.addbutton.setText(R.string.remove_button);
        }else{
            if(UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.PENDING)){
                holder.addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.colorPrimary));
                holder.addbutton.setText(R.string.pending_button);
            }else {
                if (UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.WAITING)) {
                    holder.addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.disabled_button));
                    holder.addbutton.setText(R.string.waiting_button);
                } else {
                    holder.addbutton.setBackgroundColor(SearchAdapter.sa.getResources().getColor(R.color.colorPrimary));
                    holder.addbutton.setText(R.string.add_button);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
