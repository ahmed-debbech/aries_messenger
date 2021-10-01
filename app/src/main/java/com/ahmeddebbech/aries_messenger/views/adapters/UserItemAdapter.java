package com.ahmeddebbech.aries_messenger.views.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractItemList;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.UserItemPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.util.ImageHelper;
import com.ahmeddebbech.aries_messenger.views.activities.ContactProfile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.MainViewHolder>{
    private ArrayList<ItemUser> list;
    private static AppCompatActivity sa;

    public static class MainViewHolder extends RecyclerView.ViewHolder implements ContractItemList.View {
        private ImageView photo;
        private TextView disp;
        private TextView username;
        private ImageButton addbutton;
        private ImageButton refusebutton = null;
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

            pres = new UserItemPresenter(this);
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
                    addbutton.setBackgroundResource(R.drawable.ic_waiting_connection);
                    addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorAccent));
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
                addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
                addbutton.setBackgroundResource(R.drawable.ic_disconnect_connection);
            }else {
                if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.PENDING)){
                    addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
                    addbutton.setBackgroundResource(R.drawable.ic_baseline_accept);
                    refusebutton.setVisibility(View.VISIBLE);
                }else{
                    if(UserManager.getInstance().searchForConnection(refToModel.getUid(), UserManager.WAITING)){
                        addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorAccent));
                        addbutton.setBackgroundResource(R.drawable.ic_waiting_connection);
                    }else{
                        addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
                        addbutton.setBackgroundResource(R.drawable.ic_add_connection);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        MainViewHolder mvh = new MainViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final UserItemAdapter.MainViewHolder holder, int position) {
        ItemUser currentItem = list.get(position);
        if(currentItem.getPhoto() != null){
            Picasso.get().load(currentItem.getPhoto()).into(holder.photo);
            Picasso.get().load(currentItem.getPhoto()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Bitmap bt = ImageHelper.getRoundedCornerBitmap(bitmap);
                    holder.photo.setImageBitmap(bt);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }
        holder.disp.setText(currentItem.getDisplayName());
        holder.username.setText(currentItem.getUsername());
        holder.uid.setText(currentItem.getUid());
        holder.refToModel = currentItem;
        //check how the button should be shown base on the existence of the connection
        if(UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.CONNECTED)) {
            holder.addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
            holder.addbutton.setBackgroundResource(R.drawable.ic_disconnect_connection);
        }else {
            if(UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.PENDING)){
                holder.addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
                //holder. addbutton.setBackgroundResource(R.drawable.ic_baseline_accept);
                holder.refusebutton.setVisibility(View.VISIBLE);
            }else{
                if(UserManager.getInstance().searchForConnection(holder.refToModel.getUid(), UserManager.WAITING)){
                    holder.addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorAccent));
                    //holder.addbutton.setBackgroundResource(R.drawable.ic_waiting_connection);
                }else{
                    holder.addbutton.setBackgroundColor(UserItemAdapter.sa.getResources().getColor(R.color.colorPrimary));
                    //holder.addbutton.setBackgroundResource(R.drawable.ic_add_connection);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
