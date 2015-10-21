package com.example.dongja94.sampledatabase;

import android.provider.BaseColumns;

/**
 * Created by dongja94 on 2015-10-21.
 */
public class AddressDB {
    public interface AddessTable extends BaseColumns {
        public static final String TABLE_NAME = "addressTable";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_OFFICE = "office";
    }
}
