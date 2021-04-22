package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessengerManager {

    private static MessengerManager instance;

    private MessengerManager(){

    }
    public static MessengerManager getInstance(){
        if(instance == null){
            instance = new MessengerManager();
        }
        return instance;
    }
    public void updateMessagesStatus(final String status, final List<Message> list, final String convid){
        DbConnector.connectToGetLastSeenIndex(UserManager.getInstance().getUserModel().getUid(), convid, new Presenter(){
            @Override
            public void returnData(Object obj) {
                if(obj instanceof Integer) {
                    Integer ind = (Integer)obj;
                    List<Message> nlist = new ArrayList<>();
                    for (int i=ind+1; i<=UserManager.getInstance().getCurrentConv().getCount(); i++) {
                        for(int j=0; j<=list.size()-1; j++){
                            if(list.get(j).getIndex() == i){
                                if(list.get(j).getSender_uid() != UserManager.getInstance().getUserModel().getUid()) {
                                    list.get(j).setStatus(status);
                                    nlist.add(list.get(j));
                                }
                            }
                        }
                    }
                    DbConnector.connectToSendListOfMessages(nlist, convid);
                }
            }
        });

    }
}
