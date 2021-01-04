package com.ahmeddebbech.aries_messenger.presenter;

import android.widget.Toast;

import com.ahmeddebbech.aries_messenger.contracts.ContractDNameEdit;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

public class EditDNamePresenter extends Presenter implements ContractDNameEdit.Presenter {

    private ContractDNameEdit.View act;

    public EditDNamePresenter(ContractDNameEdit.View act){
        this.act = act;
    }
    @Override
    public boolean inputIsFine(String bio) {
        if(InputChecker.isLonger(bio,32)){
            act.setError("New display name is too long!");
            return false;
        }else{
            if(bio.length() == 0){
                act.setError("Please enter your new display name.");
                return false;
            }else{
                return true;
            }
        }
    }

    @Override
    public void updateModel(String bio) {
        UserManager.getInstance().getUserModel().setDisplayName(bio);
    }

    @Override
    public void modifyUserInDB() {
        DbBasic.modifyUser(UserManager.getInstance().getUserModel());
    }

    @Override
    public void returnData(Object obj) {

    }
}
