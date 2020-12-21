package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractProfileF {
    interface View{
        void setTextsForViews(String disp, String usr, String bio, String photo);
    }
    interface Presenter{
        void getData();
    }
}
