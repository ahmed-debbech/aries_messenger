package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractBlocked;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;
import java.util.List;

public class BlockedPresenter extends Presenter implements ContractBlocked.Presenter {

    private ContractBlocked.View activity;

    public BlockedPresenter(ContractBlocked.View act){
        this.activity = act;
    }

    @Override
    public void getBlockedConnections() {
        List<String> list = UserManager.getInstance().getUserModel().getBlockedUsers();
        DbConnector.connectToConvertUidsToUsers(list, this);
    }

    @Override
    public void returnData(DatabaseOutput obj) {
        if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.CONVERT_TO_USERS){
            ArrayList<ItemUser> list = (ArrayList<ItemUser>)obj.getObj();
            activity.showBlockedConnections(list);
        }
    }
}
