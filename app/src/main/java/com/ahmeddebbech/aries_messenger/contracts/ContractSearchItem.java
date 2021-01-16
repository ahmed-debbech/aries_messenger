package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractSearchItem {
    interface View{
        void updateUi();
    }
    interface Presenter{
        void addToContact(String uid);
        void updateConnectionsList(String uid);
        void removeFromContact(String uid);
    }
}
