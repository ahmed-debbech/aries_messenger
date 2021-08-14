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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BlockedViewHolder extends RecyclerView.ViewHolder{

        public BlockedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
