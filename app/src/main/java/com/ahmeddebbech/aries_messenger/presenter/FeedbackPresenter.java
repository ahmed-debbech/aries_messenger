package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractFeedback;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

public class FeedbackPresenter extends Presenter implements ContractFeedback.Presenter{

    private ContractFeedback.View activity;

    public FeedbackPresenter(ContractFeedback.View act){
        this.activity = act;
    }

    @Override
    public void sendFeedback(String email, String desc) {
        boolean cor1 = InputChecker.isItEmail(email);
        boolean cor2 = InputChecker.isLonger(desc, 10);
        if(!cor1){
            activity.setError("Sorry, this doesn't look like an email!", ContractFeedback.View.EMAIL_FIELD);
            return;
        }
        if(cor2){
            activity.setError("Sorry, the description is too long!", ContractFeedback.View.DESCRIPTION_FIELD);
            return;
        }
        //DbConnector.connectToSendFeedback();
    }

    @Override
    public void returnData(DatabaseOutput obj) {

    }
}
