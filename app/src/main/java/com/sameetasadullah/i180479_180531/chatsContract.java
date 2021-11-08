package com.sameetasadullah.i180479_180531;

import android.provider.BaseColumns;

public class chatsContract {
    public static String DB_NAME = "chats.db";
    public static int DB_VERSION = 1;

    public static class Chats implements BaseColumns {
        public static String TABLENAME = "chatsTable";
        public static String _SENDER_NAME = "sender_name";
        public static String _RECEIVER_NAME = "receiver_name";
        public static String _MESSAGE = "message";
        public static String _TIME = "time";
    }
}
