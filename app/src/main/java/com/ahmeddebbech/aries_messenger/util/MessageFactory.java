package com.ahmeddebbech.aries_messenger.util;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;

import java.sql.Timestamp;
import java.util.Date;

public class MessageFactory {

    public static Message getMessage(Conversation conv, String content, String msg_to_reply_to_id){
        Message m = new Message();
        m.setSender_uid("gggg");
        m.setId_conv(conv.getId());
        m.setId(RandomIdGenerator.generateMessageId(conv.getId()));
        m.setIndex(conv.getCount() + 1);
        m.setContent(InputChecker.makeMessageFine(content));
        if(msg_to_reply_to_id != null){
            m.setId_reply_msg(msg_to_reply_to_id);
        }
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        m.setDate(time.toString());
        m.setStatus(Message.SENT);
        return m;
    }
}
