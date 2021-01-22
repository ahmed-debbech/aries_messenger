package com.ahmeddebbech.aries_messenger.views.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractItemList;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.RequestsItemPresenter;
import com.ahmeddebbech.aries_messenger.views.activities.ContactProfile;
import com.ahmeddebbech.aries_messenger.views.activities.RequestsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestsAdapter  extends RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {
    private ArrayList<ItemUser> list;
    private static RequestsActivity rq;
    private static RequestsAdapter rd;

    public static class RequestsViewHolder extends RecyclerView.ViewHolder implements ContractItemList.View {
        private ImageView photo;
        private TextView disp;
        private TextView username;
        private ImageView accept;
        private ImageView refuse;

        private ItemUser refToModel;
        private ContractItemList.Presenter pres;

        public RequestsViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            disp = itemView.findViewById(R.id.displayName);
            username = itemView.findViewById(R.id.username);
            accept = itemView.findViewById(R.id.accept);
            refuse = itemView.findViewById(R.id.refuse);

            pres = new RequestsItemPresenter(this);
            setClicks();
        }
        public void setClicks(){
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(RequestsAdapter.rq, ContactProfile.class);
                    in.putExtra("uid", refToModel.getUid());
                    in.putExtra("username", username.getText());
                    RequestsAdapter.rq.startActivity(in);
                }
            });
            disp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(RequestsAdapter.rq, ContactProfile.class);
                    in.putExtra("uid", refToModel.getUid());
                    in.putExtra("username", username.getText());
                    RequestsAdapter.rq.startActivity(in);
                }
            });
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });
            refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });
        }
        @Override
        public void updateUi() {
            rd.removeItem(getAdapterPosition());
        }
    }
    public final void removeItem(int pos) {
        list.remove(pos);
    }
    public RequestsAdapter(ArrayList<ItemUser> listOfItems, RequestsActivity sa) {
        list = listOfItems;
        this.rq = sa;
    }
    @NonNull
    @Override
    public RequestsAdapter.RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        RequestsAdapter.RequestsViewHolder svh = new RequestsAdapter.RequestsViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.RequestsViewHolder holder, int position) {
        ItemUser currentItem = list.get(position);
        Picasso.get().load(currentItem.getPhoto()).into(holder.photo);
        holder.disp.setText(currentItem.getDisplayName());
        holder.username.setText(currentItem.getUsername());
        holder.refToModel = currentItem;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
