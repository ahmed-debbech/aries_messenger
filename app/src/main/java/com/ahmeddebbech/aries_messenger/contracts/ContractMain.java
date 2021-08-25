package com.ahmeddebbech.aries_messenger.contracts;

public interface ContractMain {
    interface View{
        void renderViewsWithData(String disp, String usern, String image);
        void setupUi();
        void setPendingBadge(boolean set);
    }
    interface Presenter{
        void fillViewsWithUserData();
        void getConnections();
        void getConversations();
        void getBlocked();
    }
}
