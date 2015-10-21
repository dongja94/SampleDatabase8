package com.example.dongja94.sampledatabase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DBAddActivity extends AppCompatActivity {

    EditText nameView, addressView, phoneView, officeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbadd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        nameView = (EditText)findViewById(R.id.edit_name);
        addressView = (EditText)findViewById(R.id.edit_address);
        phoneView = (EditText)findViewById(R.id.edit_phone);
        officeView = (EditText)findViewById(R.id.edit_office);

        Button btn = (Button)findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameView.getText().toString();
                String address = addressView.getText().toString();
                String phone = phoneView.getText().toString();
                String office = officeView.getText().toString();

                if (!TextUtils.isEmpty(name)) {
                    AddressItem item = new AddressItem();
                    item.name = name;
                    item.address = address;
                    item.phone = phone;
                    item.office = office;
                    DataManager.getInstance().add(item);
                    nameView.setText("");
                    addressView.setText("");
                    phoneView.setText("");
                    officeView.setText("");
                }
            }
        });
    }

}
