package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractFeedback;
import com.ahmeddebbech.aries_messenger.presenter.FeedbackPresenter;

public class FeedbackActivity extends AppCompatActivity implements ContractFeedback.View {

    private ActionBar actionBar;
    private TextView email;
    private TextView desc;
    private Button submit;
    private ContractFeedback.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        presenter = new FeedbackPresenter(this);

        email = findViewById(R.id.email);
        desc = findViewById(R.id.description);
        submit = findViewById(R.id.submit);
        addListeners();
    }
    private void addListeners(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String des = desc.getText().toString();
                presenter.sendFeedback(em, des);
            }
        });
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
    public void isPushed(boolean b) {
        if(b == true){
            Toast toast=Toast.makeText(getApplicationContext(),"Your opinion has been submitted",Toast.LENGTH_SHORT);
            toast.show();
        }else{
            Toast toast=Toast.makeText(getApplicationContext(),"{ERROR} Your opinion has not been submitted",Toast.LENGTH_SHORT);
            toast.show();
        }
        finish();
    }

    @Override
    public void setError(String error, int field) {
        if(field == ContractFeedback.View.EMAIL_FIELD){
            email.setError(error);
        }else{
            if(field == ContractFeedback.View.DESCRIPTION_FIELD){
                desc.setError(error);
            }
        }
    }
}