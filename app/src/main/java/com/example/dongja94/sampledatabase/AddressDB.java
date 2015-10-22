package com.example.dongja94.sampledatabase;

import android.provider.BaseColumns;

/**
 * Created by dongja94 on 2015-10-21.
 */
public class AddressDB {

    public static String[] TYPE_NAME = {"SEND" , "RECEIVE" , "DATE"};
    public static final int TYPE_SEND = 0;
    public static final int TYPE_RECEIVE = 1;
    public static final int TYPE_DATE = 2;

    public interface AddessTable extends BaseColumns {
        public static final String TABLE_NAME = "addressTable";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_OFFICE = "office";
        public static final String COLUMN_LAST_MESSAGE_ID = "msgid";
    }

    public interface MessageTable extends BaseColumns {
        public static final String TABLE_NAME = "messageTable";
        public static final String COLUMN_USER_ID = "userid";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_CREATED = "created";
    }
}
