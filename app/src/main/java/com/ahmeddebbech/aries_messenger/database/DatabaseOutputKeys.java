package com.ahmeddebbech.aries_messenger.database;

public class DatabaseOutputKeys {
    public static String TAG_DB ="db_error";

    private  DatabaseOutputKeys(){}

    public static int ANY_ERROR = -1;

    public static int CONVS_IDS_GETTER = 0;
    public static int CHECK_NEW_MESSAGES_KEY = 1;
    public static int CONNECTIONS_UIDS = 2;
    public static int CONNECTIONS_CONVERTED = 3;
    public static int SEND_MSG = 4;
    public static int GET_CONV = 5;
    public static int GET_LAST_SEEN_INDEX = 6;
    public static int GET_USER_DATA = 7;
    public static int SEARCH_ALL_USERS_BY_NAME = 8;
    public static int CONVERT_TO_USERS = 9;
    public static int ACCEPT_CONTACT = 10;
    public static int ADD_CONTACT = 11;
    public static int GET_CONNECTIONS = 12;
    public static int REMOVE_CONTACT = 13;
    public static int USER_EXISTS = 14;
    public static int USERNAME_EXISTS = 15;
    public static int CHECK_CONV_EXISTS = 16;
    public static int GET_MESSAGES = 17;
    public static final int GET_TYPERS = 18;
    public static final int GET_USER_FROM_UID = 19;
    public static int GET_NEW_MSG = 20;
    public static int GET_USER_TYPING_NAME = 21;
    public static int GET_CHANGED_MESSAGE = 22;
    public static int GET_ONE_MESSAGE = 23;
    public static int GET_CONNECTIONS_NUMBER = 24;
    public static int GET_BLOCKED = 25;
    public static final int FEEDBACK_SENT_ACK = 26;
    public static final int GET_ACCESS_TOKEN = 27;

}
