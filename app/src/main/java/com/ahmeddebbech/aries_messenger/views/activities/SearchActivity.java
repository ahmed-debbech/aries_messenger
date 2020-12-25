package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.presenter.SearchPresenter;

public class SearchActivity extends AppCompatActivity implements ContractSearch.View {

    SearchPresenter presenter;

    ImageView back;
    ImageView delete;
    TextView input;
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
                TextView in = findViewById(R.id.searchact_input);
                in.setText("");
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.fillSearchResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void showResults() {

    }
}