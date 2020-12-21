package com.ahmeddebbech.aries_messenger.views.activities;

import android.os.Bundle;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractDNameEdit;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.EditDNamePresenter;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditDispNameActivity extends AppCompatActivity implements ContractDNameEdit.View {
    private EditDNamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_disp_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new EditDNamePresenter(this);

        Button btn = findViewById(R.id.edit_change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = findViewById(R.id.edit_input_dispName);
                if(presenter.inputIsFine(tv.getText().toString()) == true){
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