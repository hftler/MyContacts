package com.example.andi.mycontacts;

import java.util.ArrayList;

/**
 * Created by andi on 17.08.15.
 */
public class ContactList extends ArrayList<Contact> {
    public static ContactList sInstance = null;

    private ContactList(){

    }

    public static ContactList getInstance(){
        if( sInstance == null){
            sInstance = new ContactList();
        }

        return sInstance;
    }


}
