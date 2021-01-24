package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractContactProfile {
    interface View{
        void loadData(String disp, String photo, String bio);
        void updateUi();
    }
    interface Presenter{
        void fillUiWithData(String uid);
        void addToContact(String uid);
        void removeFromContact(String uid);
    }
}
