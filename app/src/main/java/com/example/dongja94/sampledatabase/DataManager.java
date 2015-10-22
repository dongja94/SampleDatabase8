package com.example.dongja94.sampledatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dongja94 on 2015-10-21.
 */
public class DataManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "address";
    private static final int DB_VERSION = 1;

    private static DataManager instance;
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+AddressDB.AddessTable.TABLE_NAME+"(" +
                AddressDB.AddessTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AddressDB.AddessTable.COLUMN_NAME+" TEXT NOT NULL," +
                AddressDB.AddessTable.COLUMN_ADDRESS+" TEXT," +
                AddressDB.AddessTable.COLUMN_PHONE+" TEXT," +
                AddressDB.AddessTable.COLUMN_OFFICE+" TEXT," +
                AddressDB.AddessTable.COLUMN_LAST_MESSAGE_ID + " INTEGER);";
        db.execSQL(sql);

        sql = "CREATE TABLE " + AddressDB.MessageTable.TABLE_NAME + "(" +
                AddressDB.MessageTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AddressDB.MessageTable.COLUMN_USER_ID + " INTEGER," +
                AddressDB.MessageTable.COLUMN_TYPE + " INTEGER," +
                AddressDB.MessageTable.COLUMN_MESSAGE + " TEXT," +
                AddressDB.MessageTable.COLUMN_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertMessage(AddressItem item, int type, String message) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();

            long currentTime = System.currentTimeMillis();

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(item.timestamp);
            int lastyear = c.get(Calendar.YEAR);
            int lastmonth = c.get(Calendar.MONTH);
            int lastday = c.get(Calendar.DAY_OF_MONTH);

            c.setTimeInMillis(currentTime);
            int currentyear = c.get(Calendar.YEAR);
            int currentmonth = c.get(Calendar.MONTH);
            int currentday = c.get(Calendar.DAY_OF_MONTH);
            ContentValues values = new ContentValues();

            if (lastyear != currentyear || lastmonth != currentmonth || lastday != currentday) {
                values.put(AddressDB.MessageTable.COLUMN_USER_ID, item._id);
                values.put(AddressDB.MessageTable.COLUMN_TYPE, AddressDB.TYPE_DATE);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strNow = sdf.format(c.getTime());
                values.put(AddressDB.MessageTable.COLUMN_MESSAGE, strNow);
                values.put(AddressDB.MessageTable.COLUMN_CREATED, currentTime);
                db.insert(AddressDB.MessageTable.TABLE_NAME, null, values);
            }

            values.clear();
            values.put(AddressDB.MessageTable.COLUMN_USER_ID, item._id);
            values.put(AddressDB.MessageTable.COLUMN_TYPE, type);
            values.put(AddressDB.MessageTable.COLUMN_MESSAGE, message);
            values.put(AddressDB.MessageTable.COLUMN_CREATED, currentTime);
            long id = db.insert(AddressDB.MessageTable.TABLE_NAME, null, values);

            values.clear();
            values.put(AddressDB.AddessTable.COLUMN_LAST_MESSAGE_ID, id);
            String selection = AddressDB.AddessTable._ID + " = ?";
            String[] args = {"" + item._id};
            db.update(AddressDB.AddessTable.TABLE_NAME, values, selection, args);

            item.timestamp = currentTime;

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getMessageCursor(long userid) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {AddressDB.MessageTable.TABLE_NAME + "." + AddressDB.MessageTable._ID,
                AddressDB.AddessTable.COLUMN_NAME,
                AddressDB.MessageTable.COLUMN_MESSAGE,
                AddressDB.MessageTable.COLUMN_TYPE,
                AddressDB.MessageTable.COLUMN_CREATED};

        String tableName = AddressDB.MessageTable.TABLE_NAME + " INNER JOIN " +
                AddressDB.AddessTable.TABLE_NAME + " ON " +
                AddressDB.MessageTable.TABLE_NAME + "." + AddressDB.MessageTable.COLUMN_USER_ID +
                " = " +
                AddressDB.AddessTable.TABLE_NAME + "." + AddressDB.AddessTable._ID;

        String selection = AddressDB.MessageTable.COLUMN_USER_ID + " = ?";
        String[] args = {"" + userid};
        String orderBy = AddressDB.MessageTable.COLUMN_CREATED + " ASC";
        return db.query(tableName,columns,selection, args, null, null, orderBy);
    }

    public void add(AddressItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.clear();
        values.put(AddressDB.AddessTable.COLUMN_NAME,item.name);
        values.put(AddressDB.AddessTable.COLUMN_ADDRESS, item.address);
        values.put(AddressDB.AddessTable.COLUMN_PHONE, item.phone);
        values.put(AddressDB.AddessTable.COLUMN_OFFICE, item.office);

        item._id = db.insert(AddressDB.AddessTable.TABLE_NAME, null, values);
    }

    public void update(AddressItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.clear();
        values.put(AddressDB.AddessTable.COLUMN_NAME,item.name);
        values.put(AddressDB.AddessTable.COLUMN_ADDRESS, item.address);
        values.put(AddressDB.AddessTable.COLUMN_PHONE, item.phone);
        values.put(AddressDB.AddessTable.COLUMN_OFFICE, item.office);

        String selection = AddressDB.AddessTable._ID + " = ?";
        String[] args = new String[] {"" + item._id};
        db.update(AddressDB.AddessTable.TABLE_NAME,values, selection, args);

    }

    public List<AddressItem> getAddressList(String keyword) {
        List<AddressItem> list = new ArrayList<AddressItem>();
        Cursor c = getAddressCursor(keyword);
        while(c.moveToNext()) {
            AddressItem item = new AddressItem();
            item._id = c.getLong(c.getColumnIndex(AddressDB.AddessTable._ID));
            item.name = c.getString(c.getColumnIndex(AddressDB.AddessTable.COLUMN_NAME));
            item.address = c.getString(c.getColumnIndex(AddressDB.AddessTable.COLUMN_ADDRESS));
            item.phone = c.getString(c.getColumnIndex(AddressDB.AddessTable.COLUMN_PHONE));
            item.office = c.getString(c.getColumnIndex(AddressDB.AddessTable.COLUMN_OFFICE));
            item.lastMessageId = c.getInt(c.getColumnIndex(AddressDB.AddessTable.COLUMN_LAST_MESSAGE_ID));
            list.add(item);
        }
        c.close();
        return list;
    }

    public Cursor getAddressCursor(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {AddressDB.AddessTable.TABLE_NAME + "." + AddressDB.AddessTable._ID,
                AddressDB.AddessTable.COLUMN_NAME,
                AddressDB.AddessTable.COLUMN_ADDRESS,
                AddressDB.AddessTable.COLUMN_PHONE,
                AddressDB.AddessTable.COLUMN_OFFICE,
                AddressDB.AddessTable.COLUMN_LAST_MESSAGE_ID,
                AddressDB.MessageTable.COLUMN_MESSAGE,
                AddressDB.MessageTable.COLUMN_CREATED};
        String tableName = AddressDB.AddessTable.TABLE_NAME + " LEFT OUTER JOIN " +
                AddressDB.MessageTable.TABLE_NAME + " ON " +
                AddressDB.AddessTable.TABLE_NAME + "." + AddressDB.AddessTable.COLUMN_LAST_MESSAGE_ID +
                " = " +
                AddressDB.MessageTable.TABLE_NAME + "." + AddressDB.MessageTable._ID;

        String selection = null;
        String[] args = null;
        if (!TextUtils.isEmpty(keyword)) {
            selection = AddressDB.AddessTable.COLUMN_NAME + " LIKE ? OR "+
                    AddressDB.AddessTable.COLUMN_ADDRESS+" LIKE ? OR "+
                    AddressDB.AddessTable.COLUMN_PHONE+" LIKE ? OR "+
                    AddressDB.AddessTable.COLUMN_OFFICE+" LIKE ?";
            args = new String[] {"%" + keyword + "%","%" + keyword + "%","%" + keyword + "%","%" + keyword + "%"};
        }

        String orderBy = AddressDB.AddessTable.COLUMN_NAME+" COLLATE LOCALIZED ASC";
        Cursor c = db.query(tableName, columns, selection, args, null, null, orderBy);
        return c;
    }


}
