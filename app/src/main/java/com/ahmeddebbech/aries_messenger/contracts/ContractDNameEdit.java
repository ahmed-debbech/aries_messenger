package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractDNameEdit {
    interface View{
        void setError(String err);
    }
    interface Presenter {
        boolean inputIsFine(String bio);
        void updateModel(String bio);
        void modifyUserInDB();
    }
}
