package com.example.andi.mycontacts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by andi on 07.08.15.
 *
 * class which save the contact
 */
public class Contact implements Serializable {

    private String mName;
    private ArrayList<String> mEmails;
    private ArrayList<String> mNumbers;

    public ArrayList<String> getEmails() {
        return mEmails;
    }

    public void setEmails(ArrayList<String> emails) {
        mEmails = emails;
    }

    public ArrayList<String> getNumbers() {
        return mNumbers;
    }

    public void setNumbers(ArrayList<String> numbers) {
        mNumbers = numbers;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
