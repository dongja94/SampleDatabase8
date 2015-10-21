package com.example.dongja94.sampledatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
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
                AddressDB.AddessTable.COLUMN_OFFICE+" TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
            list.add(item);
        }
        return list;
    }

    public Cursor getAddressCursor(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"_id", "name", "address", "phone", "office"};
        String selection = null;
        String[] args = null;
        if (!TextUtils.isEmpty(keyword)) {
            selection = "name LIKE ? OR address LIKE ? OR phone LIKE ? OR office LIKE ?";
            args = new String[] {"%" + keyword + "%","%" + keyword + "%","%" + keyword + "%","%" + keyword + "%"};
        }
        String orderBy = " name COLLATE LOCALIZED ASC";
        Cursor c = db.query("addressTable", columns, selection, args, null, null, orderBy);
        return c;
    }
}