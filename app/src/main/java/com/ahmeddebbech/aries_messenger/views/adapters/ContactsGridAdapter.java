package com.ahmeddebbech.aries_messenger.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.ahmeddebbech.aries_messenger.presenter.MessengerManager;
import com.ahmeddebbech.aries_messenger.util.ImageHelper;
import com.ahmeddebbech.aries_messenger.views.activities.ConversationActivity;
import com.ahmeddebbech.aries_messenger.views.activities.LoginActivity;
import com.ahmeddebbech.aries_messenger.views.activities.MainActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class ContactsGridAdapter extends RecyclerView.Adapter<ContactsGridAdapter.ContactViewHolder> {
    private List<ItemUser> list;
    private static Context parent_activity;

    public ContactsGridAdapter(List<ItemUser> l, Context act){
        parent_activity= act;
        this.list = l;
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        private TextView displayname;
        private ImageView iv;
        private CardView cv;
        private TextView last_message;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            displayname = itemView.findViewById(R.id.ct_displayname);
            iv = itemView.findViewById(R.id.ct_photo);
            cv = itemView.findViewById(R.id.card);
            last_message = itemView.findViewById(R.id.last_message);
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
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, int position) {
        //associate the data
        ItemUser i = list.get(position);
        final String uid = i.getUid();
        final String display = i.getDisplayName();
        final String username = i.getUsername();
        final String photo = i.getPhoto();
        holder.displayname.setText(i.getDisplayName());
        if(i.getPhoto() != null) {
            Picasso.get().load(i.getPhoto()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Bitmap bt = ImageHelper.getRoundedCornerBitmap(bitmap);
                    holder.iv.setImageBitmap(bt);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cv.setBackground(holder.itemView.getResources().getDrawable(R.drawable.default_background_contact_item));
                MessengerManager.getInstance().removeFromLatestUpdatedConvs(uid);
                Intent i = new Intent(parent_activity, ConversationActivity.class);
                i.putExtra("uid", uid);
                parent_activity.startActivity(i);
            }
        });
        if(MessengerManager.getInstance().hasNewMessages(uid)) {
            holder.cv.setBackground(holder.itemView.getResources().getDrawable(R.drawable.background_gradient));
        }else{
            holder.cv.setBackground(holder.itemView.getResources().getDrawable(R.drawable.default_background_contact_item));
            holder.displayname.setTextColor(holder.itemView.getResources().getColor(R.color.naturalText));
            holder.last_message.setTextColor(holder.itemView.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
