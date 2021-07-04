package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractRequests;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.RequestsPresenter;
import com.ahmeddebbech.aries_messenger.util.ItemSpacing;
import com.ahmeddebbech.aries_messenger.views.adapters.UserItemAdapter;

import java.util.ArrayList;

public class RequestsActivity extends AppCompatActivity implements ContractRequests.View {

    private ActionBar actionBar;
    private RequestsPresenter pres;

    //ui components
    private ProgressBar wait;
    private TextView no_results_msg;
    private RecyclerView results;
    private UserItemAdapter adapter;
    private SwipeRefreshLayout srl;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        pres = new RequestsPresenter(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Pending Requests");

        wait = findViewById(R.id.wait);
        no_results_msg = findViewById(R.id.no_results_msg);
        results = findViewById(R.id.results);
        srl = findViewById(R.id.request_swipe_refresh);
        results.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        results.setLayoutManager(layoutManager);

        wait.setVisibility(View.VISIBLE);
        addListeners();
    }

    private void addListeners(){
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pres.seekForPendingRequest();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        pres.seekForPendingRequest();
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
    public void showResults(ArrayList<ItemUser> listOfItems) {
        srl.setRefreshing(false);
        wait.setVisibility(View.INVISIBLE);
        if(listOfItems.isEmpty() == true){
            no_results_msg.setVisibility(View.VISIBLE);
            results.setAdapter(null);
        }else {
            no_results_msg.setVisibility(View.INVISIBLE);
            ItemSpacing itemDec = new ItemSpacing(15);
            results.addItemDecoration(itemDec);
            adapter = new UserItemAdapter(listOfItems, this);
            results.setAdapter(adapter);
        }
    }
}