package com.example.dongja94.sampledatabase;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by dongja94 on 2015-10-22.
 */
public class MyCursorAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_COUNT = 3;

    private static final int VIEW_INDEX_SEND = 0;
    private static final int VIEW_INDEX_RECEIVE = 1;
    private static final int VIEW_INDEX_DATE = 2;

    public static class ViewHolder {
        TextView messageView;
    }

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        Cursor c = (Cursor) getItem(position);
        int type = c.getInt(c.getColumnIndex(AddressDB.MessageTable.COLUMN_TYPE));
        switch (type) {
            case AddressDB.TYPE_SEND:
                return VIEW_INDEX_SEND;
            case AddressDB.TYPE_RECEIVE:
                return VIEW_INDEX_RECEIVE;
            case AddressDB.TYPE_DATE:
            default:
                return VIEW_INDEX_DATE;
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int type = cursor.getInt(cursor.getColumnIndex(AddressDB.MessageTable.COLUMN_TYPE));
        switch (type) {
            case AddressDB.TYPE_SEND: {
                View view = LayoutInflater.from(context).inflate(R.layout.view_send, null);
                ViewHolder holder = new ViewHolder();
                holder.messageView = (TextView) view.findViewById(R.id.text_message);
                view.setTag(holder);
                return view;
            }
            case AddressDB.TYPE_RECEIVE: {
                View view = LayoutInflater.from(context).inflate(R.layout.view_receive, null);
                ViewHolder holder = new ViewHolder();
                holder.messageView = (TextView) view.findViewById(R.id.text_message);
                view.setTag(holder);
                return view;
            }
            case AddressDB.TYPE_DATE:
            default: {
                View view = LayoutInflater.from(context).inflate(R.layout.view_date, null);
                ViewHolder holder = new ViewHolder();
                holder.messageView = (TextView) view.findViewById(R.id.text_message);
                view.setTag(holder);
                return view;
            }

        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int type = cursor.getInt(cursor.getColumnIndex(AddressDB.MessageTable.COLUMN_TYPE));
        switch (type) {
            case AddressDB.TYPE_SEND:
            case AddressDB.TYPE_RECEIVE:
            case AddressDB.TYPE_DATE:
            default:
                ViewHolder holder = (ViewHolder) view.getTag();
                String message = cursor.getString(cursor.getColumnIndex(AddressDB.MessageTable.COLUMN_MESSAGE));
                holder.messageView.setText(message);
        }
    }
}
