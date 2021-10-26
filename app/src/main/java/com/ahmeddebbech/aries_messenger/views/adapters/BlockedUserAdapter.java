package com.ahmeddebbech.aries_messenger.views.adapters;

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
import com.ahmeddebbech.aries_messenger.presenter.UserItemPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BlockedUserAdapter extends RecyclerView.Adapter<BlockedUserAdapter.BlockedViewHolder>{

    private ArrayList<ItemUser> list;

    public BlockedUserAdapter(ArrayList<ItemUser> list){
        this.list = list;
    }

    @NonNull
    @Override
    public BlockedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blocked_user, parent, false);
        BlockedUserAdapter.BlockedViewHolder mvh = new BlockedUserAdapter.BlockedViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BlockedViewHolder holder, int position) {
        ItemUser currentItem = list.get(position);
        Picasso.get().load(currentItem.getPhoto()).into(holder.photo);
        holder.dispname.setText(currentItem.getDisplayName());
        holder.username.setText(currentItem.getUsername());
        holder.ref = currentItem;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BlockedViewHolder extends RecyclerView.ViewHolder implements ContractItemList.View {

        private ImageView photo;
        private TextView dispname;
        private TextView username;
        private Button unblock;
        private ItemUser ref;

        private ContractItemList.Presenter presenter;

        public BlockedViewHolder(@NonNull View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.photo);
            dispname = itemView.findViewById(R.id.displayName);
            username = itemView.findViewById(R.id.username);
            unblock = itemView.findViewById(R.id.item_block);

            presenter = new UserItemPresenter(this);

            unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.unblockConnection(ref.getUid());
                }
            });
        }

        @Override
        public void updateUi() {

        }
    }
}
