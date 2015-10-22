package com.example.dongja94.sampledatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

public class ChattingActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "item";

    AddressItem mItem;

    ListView listView;
    EditText inputView;
    RadioGroup groupView;
    MyCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mItem = (AddressItem)getIntent().getSerializableExtra(EXTRA_ITEM);

        inputView = (EditText)findViewById(R.id.edit_input);
        groupView = (RadioGroup)findViewById(R.id.radioGroup);
        listView = (ListView)findViewById(R.id.listView);

        Cursor c = DataManager.getInstance().getMessageCursor(mItem._id);
        mAdapter = new MyCursorAdapter(this, c);
        listView.setAdapter(mAdapter);

        Button btn = (Button)findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = inputView.getText().toString();
                int type = AddressDB.TYPE_SEND;
                switch (groupView.getCheckedRadioButtonId()) {
                    case R.id.radio_send :
                        type = AddressDB.TYPE_SEND;
                        break;
                    case R.id.radio_receive :
                        type = AddressDB.TYPE_RECEIVE;
                        break;
                }
                DataManager.getInstance().insertMessage(mItem, type, message);
                Cursor c = DataManager.getInstance().getMessageCursor(mItem._id);
                mAdapter.changeCursor(c);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.changeCursor(null);
    }
}
