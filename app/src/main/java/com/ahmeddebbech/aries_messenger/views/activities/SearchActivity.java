package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.model.SearchItem;
import com.ahmeddebbech.aries_messenger.presenter.SearchPresenter;
import com.ahmeddebbech.aries_messenger.views.adapter.SearchAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ContractSearch.View {

    private SearchPresenter presenter;

    private ImageView back;
    private  ImageView delete;
    private TextView input;
    private RecyclerView results;
    private SearchAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new SearchPresenter(this);

        setupUi();
        addListeners();
    }
    public void setupUi(){
        back = findViewById(R.id.searchact_back);
        delete = findViewById(R.id.searchact_delete);
        input = findViewById(R.id.searchact_input);
        results = findViewById(R.id.results);
        results.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        results.setLayoutManager(layoutManager);
    }

    public void addListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.fillSearchResults(s.toString());
            }
        });
    }

    @Override
    public void showResults(ArrayList<SearchItem> listOfItems) {
        adapter = new SearchAdapter(listOfItems);
        results.setAdapter(adapter);
    }
}