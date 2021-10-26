package com.ahmeddebbech.aries_messenger.views.activities;

import android.os.Bundle;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractDNameEdit;
import com.ahmeddebbech.aries_messenger.presenter.EditDNamePresenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditDispNameActivity extends AppCompatActivity implements ContractDNameEdit.View {
    private EditDNamePresenter presenter;
    private ActionBar actionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_disp_name);

        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        presenter = new EditDNamePresenter(this);

        setupUi();
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
        Button btn = findViewById(R.id.edit_change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            TextView tv = findViewById(R.id.edit_input_dispName);
            if(presenter.inputIsFine(tv.getText().toString()) == true) {
                presenter.updateModel(tv.getText().toString());
                presenter.modifyUserInDB();
                Toast toast = Toast.makeText(getApplicationContext(), "Display name updated successfully!", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
            }
        });
    }
    @Override
    public void setError(String err) {

    }
}