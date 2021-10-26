package com.ahmeddebbech.aries_messenger.util;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;

import java.util.List;

public class ConversationFactory {

    public static Conversation getConversation(List<String> members){
        Conversation cv = null;
        cv = new Conversation();
        cv.setId(RandomIdGenerator.generateConversationId(UserManager.getInstance().getUserModel().getUid(), members.get(0)));
        cv.setCount(0);
        cv.setMembers(members);
        cv.setLatest_msg("");
        return cv;
    }
}
