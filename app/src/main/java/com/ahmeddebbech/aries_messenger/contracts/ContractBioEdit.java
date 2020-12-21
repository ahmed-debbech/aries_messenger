package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractBioEdit {
    interface View{
        void updateBioCharCount(String count);
        void setTextColor(int color);
        void setError(String err);
    }
    interface Presenter{
        void controlInputBio(String bio);
        boolean inputIsFine(String bio);
        void updateModel(String bio);
        void modifyUserInDB();
    }
}
