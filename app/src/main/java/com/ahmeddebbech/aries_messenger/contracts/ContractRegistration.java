package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractRegistration {
    interface View{
        void fillWithDispName(String name);
        void showErrorUsername(String err);
        void showErrorDisplayName(String err);
    }
    interface Presenter{
        void getDispNameFromModel();
        void checkUsername(String in);
        void checkDispName(String in);
        void updateUserModel(String disp, String user);
        void connectToRegister();
    }
}
