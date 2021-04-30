package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;
import android.widget.Toast;

import com.ahmeddebbech.aries_messenger.contracts.ContractLogin;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.User;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginPresenter extends Presenter implements ContractLogin.Presenter {
    List<AuthUI.IdpConfig> providers;
    ContractLogin.View cur_act;

    public LoginPresenter(ContractLogin.View act){
        providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());
        cur_act = act;
    }
    @Override
    public FirebaseUser getLastSignedIn(){
        FirebaseUser account = FirebaseAuth.getInstance().getCurrentUser();
        if(account != null){
            return account;
        }
        return null;
    }

    @Override
    public void loginAsGoogle() {
        cur_act.showSignInIntent(providers);
    }

    @Override
    public void connectToSignIn() {
        DbConnector.connectToSignIn(this);
    }

    @Override
    public void fillModel(User user) {
        UserManager um = UserManager.getInstance();
        um.updateWithCopy(user);
    }

    @Override
    public void returnData(DatabaseOutput o) {
        if(o.getDatabaseOutputkey() == DatabaseOutputKeys.USER_EXISTS) {
            Boolean userIsFound = (Boolean)o.getObj();
            if (!userIsFound) {
                cur_act.redirectRegisterActivity();
            } else {
                Log.d("LOGIN ", "not found");
                cur_act.redirectMainActivity();
            }
        }
    }
}
