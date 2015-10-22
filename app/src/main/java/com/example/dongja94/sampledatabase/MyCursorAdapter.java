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

    public static class ViewHolder {
        TextView typeView;
        TextView messageView;
    }

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_message, null);
        ViewHolder holder = new ViewHolder();
        holder.typeView = (TextView)view.findViewById(R.id.text_type);
        holder.messageView = (TextView)view.findViewById(R.id.text_message);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();
        int type = cursor.getInt(cursor.getColumnIndex(AddressDB.MessageTable.COLUMN_TYPE));
        String message = cursor.getString(cursor.getColumnIndex(AddressDB.MessageTable.COLUMN_MESSAGE));
        holder.typeView.setText(AddressDB.TYPE_NAME[type]);
        holder.messageView.setText(message);
    }
}
