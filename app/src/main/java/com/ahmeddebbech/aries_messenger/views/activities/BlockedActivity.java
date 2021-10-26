package com.ahmeddebbech.aries_messenger.views.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.ahmeddebbech.aries_messenger.contracts.ContractBlocked;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.BlockedPresenter;
import com.ahmeddebbech.aries_messenger.views.adapters.BlockedUserAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ahmeddebbech.aries_messenger.R;

import java.util.ArrayList;

public class BlockedActivity extends AppCompatActivity implements ContractBlocked.View {

    private ActionBar actionBar;
    private RecyclerView recycler;
    private BlockedUserAdapter adapter;
    private RecyclerView.LayoutManager lm;
    private ContractBlocked.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recycler = findViewById(R.id.blocked_recycler);
        recycler.setHasFixedSize(true);
        lm = new LinearLayoutManager(this);
        recycler.setLayoutManager(lm);

        presenter = new BlockedPresenter(this);

    }
    @Override
    public  void onStart() {
        super.onStart();

        presenter.getBlockedConnections();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showBlockedConnections(ArrayList<ItemUser> result) {
        adapter = new BlockedUserAdapter(result);
        recycler.setAdapter(adapter);
    }
}