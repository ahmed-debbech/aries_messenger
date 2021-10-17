package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.SearchPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.util.ItemSpacing;
import com.ahmeddebbech.aries_messenger.views.adapters.UserItemAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ContractSearch.View {

    private SearchPresenter presenter;

    private ImageView delete;
    private TextView input;
    private ProgressBar wait;
    private TextView no_results_msg;
    private RecyclerView results;
    private UserItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter = new SearchPresenter(this);

        setupUi();
        addListeners();
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
    public void setupUi(){
        delete = findViewById(R.id.searchact_delete);
        input = findViewById(R.id.searchact_input);
        results = findViewById(R.id.results);
        no_results_msg = findViewById(R.id.no_results_msg);
        wait = findViewById(R.id.wait);
        results.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        results.setLayoutManager(layoutManager);
    }

    public void addListeners(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = findViewById(R.id.searchact_input);
                input.setText("");
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wait.setVisibility(View.VISIBLE);
                UserManager.getInstance().getAccessTokenFromDb();
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.fillSearchResults(s.toString());
            }
        });
    }

    @Override
    public void showResults(ArrayList<ItemUser> listOfItems) {
        wait.setVisibility(View.INVISIBLE);
        if(listOfItems.isEmpty() == true){
            no_results_msg.setVisibility(View.VISIBLE);
            results.setAdapter(null);
        }else {
            no_results_msg.setVisibility(View.INVISIBLE);
            ItemSpacing itemDec = new ItemSpacing(15);
            adapter = new UserItemAdapter(listOfItems, this);
            results.setAdapter(adapter);
            results.addItemDecoration(itemDec);
        }
    }
    @Override
    public void clearList(){
        adapter = null;
        results.setAdapter(adapter);
        wait.setVisibility(View.INVISIBLE);
    }
}