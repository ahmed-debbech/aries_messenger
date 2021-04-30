package com.ahmeddebbech.aries_messenger.presenter;

import android.graphics.Color;
import android.widget.Toast;

import com.ahmeddebbech.aries_messenger.contracts.ContractBioEdit;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

public class EditBioPresenter extends Presenter implements ContractBioEdit.Presenter {
    private ContractBioEdit.View act;

    public EditBioPresenter(ContractBioEdit.View act){
        this.act = act;
    }
    @Override
    public void controlInputBio(String bio) {
        int sum = 140 - bio.length();
        act.updateBioCharCount(String.valueOf(sum));
        if(sum < 0) {
            act.setTextColor(Color.RED);
        }else{
            act.setTextColor(Color.GRAY);
        }
    }

    @Override
    public boolean inputIsFine(String bio) {
        if(InputChecker.isLonger(bio,140)){
            act.setError("New bio is too long!");
            return false;
        }else{
            if(bio.length() == 0){
                act.setError("Please enter a bio to change it.");
                return false;
            }else{
                return true;
            }
        }
    }

    @Override
    public void updateModel(String bio) {
        UserManager.getInstance().getUserModel().setBio(bio);
    }

    @Override
    public void modifyUserInDB() {
        DbConnector.connectToModifyUser(UserManager.getInstance().getUserModel());
    }

    @Override
    public void returnData(DatabaseOutput obj) {

    }
}
