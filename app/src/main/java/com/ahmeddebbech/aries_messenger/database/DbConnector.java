package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.Feedback;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.ConversationPresenter;
import com.ahmeddebbech.aries_messenger.presenter.FeedbackPresenter;
import com.ahmeddebbech.aries_messenger.presenter.LoginPresenter;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.presenter.UserItemPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;

import java.util.List;

public class DbConnector {

    public static BackendServiceApi backendServiceApi;
    public static void connectToSignIn(LoginPresenter log) {
        DbUtil.userExistsInSignIn(log);
    }
    public static void connectToRegister(User user){
        DbBasic.addUserToDatabase(user);
    }
    public static void connectToAddNewContact(String uidUser, String uidAdded, Presenter pres){
        DbBasic.addContact(uidUser, uidAdded,pres);
    }
    public static void connectToModifyUser(User u){DbBasic.modifyUser(u);}
    public static void connectToGetUserData(String uid, Presenter pres){
        DbBasic.getUserData(uid, pres);
    }
    public static void connectToSearchForUsersWithName(String text, Presenter pres){DbBasic.searchAllUsersByName(text, pres);}
    public static void connectToGetConnections(String uid, Presenter pres){
        DbBasic.getConnections(uid, pres);
    }
    public static void connectToRemoveContact(String uid, String delUid, Presenter pres){
        DbBasic.removeContact(uid, delUid, pres);
    }
    public static void connectToConvertUidsToUsers(List<String> uids, Presenter pres){
        DbBasic.convertUidsToUsers(uids ,pres);
    }
    public static void connectToAcceptContact(String uidUser, String addedUid, Presenter pres){
        DbBasic.acceptContact( uidUser,  addedUid,  pres);
    }
    public static void connectToGetOneMessage(String conv_id, String msg_id, Presenter pres){
        DbConversations.getOneMessage(conv_id,msg_id, pres);
    }
    public static void connectToGetOneConversation(String uidA, String uidB, Presenter pres){
        DbConversations.getConversation(uidA, uidB, pres);
    }
    public static void connectToGetMessages(String conv_id, Presenter presenter){
        DbConversations.getMessages(conv_id, presenter);
    }
    public static void connectToGetLastSeenIndex(String uid, String convid, Presenter pres){
        DbConversations.getLastSeenIndex(uid, convid, pres);
    }
    public static void connectToSendListOfMessages(List<Message> list, String conv){
        DbConversations.sendListOfMessages(list, conv);
    }
    public static void connectToCheckNewMessages(String uid, List<String> convList, Presenter pres){
        DbConversations.checkNewMessages(uid, convList, pres);
    }
    public static void connectToGetConversationsIds(String uid, Presenter pres){
        DbConversations.getConversationsIds(uid, pres);
    }
    public static void connectToEditMsg(String id, String msg_id, String msg_cont) {
        DbConversations.editMsg(id, msg_id, msg_cont);
    }
    public static void connectToCreateConversation(Conversation cv, Presenter pres) {
        DbConversations.createConversation(cv,pres);
    }
    public static void connectToSendMessage(String id, Message m, Conversation cv) {
        DbConversations.sendMessage(id,m, cv);
    }
    public static void connectToTrackWhosTyping(String id, ConversationPresenter conversationPresenter) {
        DbConversations.trackWhosTyping(id, conversationPresenter);
    }
    public static void connectToUpdateConversation(Conversation convid, String uid){
        DbConversations.updateConversation(convid, uid);
    }
    public static void connectToGetUserByUid(String uid, int flag, Presenter pres, boolean isSingleEvent){
        DbBasic.getUserByUid(uid,flag, pres, isSingleEvent);
    }
    public static void connectToSendTypingSignal(String uid, String convid, boolean signal){
        DbConversations.sendTypingSignal(uid, convid, signal);
    }
    public static void connectToSetAvailabilityStatus(String uid, int status) {
        DbBasic.setAvailabilityStatus(uid, status);
    }
    public static void connectToGetNewMessage(String convid, Presenter pres){
        DbConversations.getNewMessages(convid ,pres);
    }
    public static void connectToUpdateMessageState(String state, String convid, String msg_id){
        DbConversations.updateMessageState(state, convid, msg_id);
    }
    public static void connectToGetUserConnectionsNumber(String uid, Presenter pres) {
        DbBasic.getConnectionsNumber(uid, pres);
    }

    public static void connectToUnblockConnection(String uid, String uid1, UserItemPresenter userItemPresenter) {
        DbBasic.unblockConnection(uid, uid1, userItemPresenter);
    }

    public static void connectToBlockConnection(String uid, String uid1) {
        DbBasic.blockConnection(uid, uid1);
    }
    public static void connectToGetBlockedUsers(String uid, Presenter pres){
        DbBasic.getBlockedUsers(uid, pres);
    }

    public static void connectToSendFeedback(Feedback fb, Presenter pres) {
        DbBasic.sendFeedback(fb, pres);
    }
    public static void connectToGetUserAccessToken(Presenter p ){
        DbUtil.getUserAccessToken(p);
    }
}
