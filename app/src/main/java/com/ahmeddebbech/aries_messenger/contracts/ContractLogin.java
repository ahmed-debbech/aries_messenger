package com.ahmeddebbech.aries_messenger.contracts;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface ContractLogin {
    interface View{
        void showSignInIntent(List<AuthUI.IdpConfig> providers);
    }
    interface Presenter{
        FirebaseUser getLastSignedIn();
        void loginAsGoogle();
    }
}
