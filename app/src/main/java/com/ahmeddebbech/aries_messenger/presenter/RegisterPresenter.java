package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractRegistration;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbUtil;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

public class RegisterPresenter extends Presenter implements ContractRegistration.Presenter {
    private ContractRegistration.View act;

    public RegisterPresenter(ContractRegistration.View act){
        this.act = act;
    }
    @Override
    public void getDispNameFromModel() {
        act.fillWithDispName(UserManager.getInstance().getUserModel().getDisplayName());
    }
    @Override
    public void checkUsername(String in){
        if(!InputChecker.noSpaces(in)){
            act.showErrorUsername("Username must not contain spaces!");
        }else{
            if(InputChecker.isLonger(in, 24)){
                act.showErrorUsername("Your username is too long!");
            }else{
                if(!InputChecker.startsWithAlt(in)){
                    act.showErrorUsername("You must use '@' at the beginning.");
                }else{
                    if(!InputChecker.usesOnlyAllowedChars(in, new char[]{'-','_','.'})){
                        act.showErrorUsername("Please use [A..Z], [a..z], [0..9], ['-','_','.'] only.");
                    }else{
                        //check if username exists
                        DbUtil.usernameExists(in,this);
                    }
                }
            }
        }
    }
    @Override
    public void checkDispName(String in){
        if(InputChecker.isLonger(in, 32)){
            act.showErrorDisplayName("Your Display Name is too long!");
        }
    }

    @Override
    public void updateUserModel(String disp, String user) {
        UserManager.getInstance().getUserModel().setDisplayName(disp);
        UserManager.getInstance().getUserModel().setUsername(user);
    }
    @Override
    public void returnData(Object o) {
        if(o instanceof String) {
            String err = (String)o;
            act.showErrorUsername(err);
        }
    }
    public void connectToRegister(){
        DbConnector.connectToRegister(UserManager.getInstance().getUserModel());
    }
}
