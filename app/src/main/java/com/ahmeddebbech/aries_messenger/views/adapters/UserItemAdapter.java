package com.ahmeddebbech.aries_messenger.views.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractItemList;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.SearchItemPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.activities.ContactProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.MainViewHolder>{
    private ArrayList<ItemUser> list;
    private static AppCompatActivity sa;

    public static class MainViewHolder extends RecyclerView.ViewHolder implements ContractItemList.View {
        private ImageView photo;
        private TextView disp;
        private TextView username;
        private Button addbutton;
        private Button refusebutton = null;
        private TextView uid;
        private ItemUser refToModel;
        private ContractItemList.Presenter pres;

        public MainViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            disp = itemView.findViewById(R.id.displayName);
            username = itemView.findViewById(R.id.username);
            addbutton = itemView.findViewById(R.id.item_add);
            refusebutton = itemView.findViewById(R.id.item_refuse);

            uid = itemView.findViewById(R.id.uid);

            pres = new SearchItemPresenter(this);
            setClicks();
        }
        public void setClicks(){
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(UserItemAdapter.sa, ContactProfile.class);
                    in.putExtra("uid", uid.getText());
                    in.putExtra("username", username.getText());
                    UserItemAdapter.sa.startActivity(in);
                }
            });
            disp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(UserItemAdapter.sa, ContactProfile.class);
                    in.putExtra("uid", uid.getText());
                    in.putExtra("username", username.getText());
                    UserItemAdapter.sa.startActivity(in);
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
                            pres.acceptContact(uid.getText().toString());
                            refusebutton.setVisibility(View.INVISIBLE);
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
            refusebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refusebutton.setVisibility(View.INVISIBLE);
                    pres.removeFromContact(refToModel.getUid());
                }
            });
        }
        @Override
        public void updateUi() {
            if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.CONNECTED)) {
                addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.disabled_button));
                addbutton.setText(R.string.remove_button);
            }else {
                if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.PENDING)){
                    addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
                    addbutton.setText(R.string.accept_button);
                    refusebutton.setVisibility(View.VISIBLE);
                }else{
                    if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.WAITING)){
                        addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.disabled_button));
                        addbutton.setText(R.string.waiting_button);
                    }else{
                        addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
                        addbutton.setText(R.string.add_button);
                    }
                }
            }
        }
    }

    public UserItemAdapter(ArrayList<ItemUser> listOfItems, AppCompatActivity sa) {
        list = listOfItems;
        this.sa = sa;
    }
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        MainViewHolder mvh = new MainViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemAdapter.MainViewHolder holder, int position) {
        ItemUser currentItem = list.get(position);
        Picasso.get().load(currentItem.getPhoto()).into(holder.photo);
        holder.disp.setText(currentItem.getDisplayName());
        holder.username.setText(currentItem.getUsername());
        holder.uid.setText(currentItem.getUid());
        holder.refToModel = currentItem;
        //check how the button should be shown base on the existance of the connection
        if(UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.CONNECTED)){
            holder.addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.disabled_button));
            holder.addbutton.setText(R.string.remove_button);
        }else{
            if(UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.PENDING)){
                holder.addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
                holder.addbutton.setText(R.string.accept_button);
                holder.refusebutton.setVisibility(View.VISIBLE);
            }else {
                if (UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.WAITING)) {
                    holder.addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.disabled_button));
                    holder.addbutton.setText(R.string.waiting_button);
                } else {
                    holder.addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
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
