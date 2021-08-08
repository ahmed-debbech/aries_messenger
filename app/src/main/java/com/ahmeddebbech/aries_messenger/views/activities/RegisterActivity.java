package com.ahmeddebbech.aries_messenger.views.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractRegistration;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbUtil;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.RegisterPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

public class RegisterActivity extends AppCompatActivity implements ContractRegistration.View {
    private RegisterPresenter presenter;

    private EditText username;
    private EditText displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new RegisterPresenter(this);
        setupUi();
        addListeners();
    }
    public void setupUi(){
        username  = (EditText)findViewById(R.id.register_username);
        displayName = (EditText)findViewById(R.id.register_disp_name);
        presenter.getDispNameFromModel();
    }
    public void addListeners(){
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkUsername(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
        });
        displayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.checkDispName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void onProceedClicked(View v){
        boolean fine = true;
        if(displayName.getText().toString().equals("")){
            displayName.setError("This field should not be empty!");
            fine =false;
        }
        if(username.getText().toString().equals("")){
            username.setError("This field should not be empty!");
            fine = false;
        }
        if(fine) {
            presenter.updateUserModel(displayName.getText().toString().trim(),
                    username.getText().toString().trim());

            presenter.connectToRegister();
            Toast toast = Toast.makeText(this, "Account created! Please login to your brand new account.", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        username = null;
        displayName = null;
    }

    @Override
    public void fillWithDispName(String name) {
        displayName.setText(name);
    }

    @Override
    public void showErrorUsername(String err) {
        username.setError(err);
    }

    @Override
    public void showErrorDisplayName(String err) {
        displayName.setError(err);
    }

}