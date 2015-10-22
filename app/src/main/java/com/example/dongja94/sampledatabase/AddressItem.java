package com.example.dongja94.sampledatabase;

import java.io.Serializable;

/**
 * Created by dongja94 on 2015-10-21.
 */
public class AddressItem implements Serializable {
    long _id = -1;
    String name;
    String address;
    String phone;
    String office;
    long lastMessageId = -1;
    String lastMessage;
    long timestamp;

    @Override
    public String toString() {
        return name + "(" + address + ")";
    }
}
