package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractConnectionsFrag;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionsFragPresenter extends Presenter implements ContractConnectionsFrag.Presenter {

    private ContractConnectionsFrag.View frag;

    public ConnectionsFragPresenter(ContractConnectionsFrag.View v){
        this.frag = v;
    }
    @Override
    public void loadContacts(String uid) {
        Log.d("##WW", "here");
        DbConnector.connectToGetConnections(uid, this);
    }

    @Override
    public void returnData(Object obj) {
        Log.d("##WW", "here1");
        if(obj instanceof Map){
            Log.d("##WW", "here2");
            Map<String, String> map = (Map<String, String>) obj;
            if(map.size() > 0) {
                List<String> li = new ArrayList<>();
                for (Map.Entry<String, String> m : map.entrySet()) {
                    li.add(m.getKey());
                }
                DbConnector.connectToConvertUidsToUsers(li, this);
            }
        }else{
            if(obj instanceof List){
                Log.d("##WW", "here3");
                List<ItemUser> lis = (List<ItemUser>)obj;
                frag.showContacts(lis);
            }
        }
    }
}
