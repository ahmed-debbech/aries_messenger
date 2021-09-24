package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractFeedback {
    interface View{
        int EMAIL_FIELD = 1;
        int DESCRIPTION_FIELD = 2;

        void isPushed(boolean b);
        void setError(String error, int field);
    }
    interface Presenter{
        void sendFeedback(String email, String desc);
    }
}
