package com.example.andi.mycontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactEditActivity extends AppCompatActivity {

    public final static String EDIT_CONTACT = "edit_contact";
    private Contact mEditContact;
    private EditText mEditText_Name;
    private LinearLayout mNumbers;
    private LinearLayout mEmails;
    private TextView mAddNewPhone;
    private TextView mAddNewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
        //get the contact and deserialize it
        if(getIntent() != null ){
            int position = getIntent().getIntExtra(EDIT_CONTACT,0);
            mEditContact = ContactList.getInstance().get(position);
        }

        //find contact_edit_name and set the name of the contact
        mEditText_Name = (EditText) findViewById(R.id.contact_edit_name);
        mEditText_Name.setText(mEditContact.getName());

        //get the phone Number section, add the phone numbers of contact
        mNumbers = (LinearLayout) findViewById(R.id.contact_edit_phone);
        for(String string : mEditContact.getNumbers()){
            EditText et = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(lp);
            et.setText(string);
            mNumbers.addView(et);
        }
        //get the addNew Text and setOnClickListener
        mAddNewPhone = (TextView) findViewById(R.id.add_new_phonenumber);
        mAddNewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = new EditText(ContactEditActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                et.setLayoutParams(lp);
                mNumbers.addView(et);

            }
        });

        //get the email section, add the emails of contact
        mEmails = (LinearLayout) findViewById(R.id.contact_edit_mail);
        for(String string : mEditContact.getEmails()){
            EditText et = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(lp);
            et.setText(string);
            mEmails.addView(et);
        }

        //get the addNew Text and setOnClickListener
        mAddNewEmail = (TextView) findViewById(R.id.add_new_email);
        mAddNewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = new EditText(ContactEditActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                et.setLayoutParams(lp);
                mEmails.addView(et);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_edit_toolbar);
        toolbar.setTitle(getResources().getString(R.string.edit_contact));
        toolbar.setNavigationIcon(R.drawable.ic_done);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.contact_edit_name);
                mEditContact.setName(name.getText().toString());

                mEditContact.setNumbers(getSections(R.id.contact_edit_phone));
                mEditContact.setEmails(getSections(R.id.contact_edit_mail));

                Toast.makeText(ContactEditActivity.this, "Saved contact", Toast.LENGTH_LONG)
                        .show();

                finish();


            }
        });
    }

    private ArrayList<String> getSections(int layout){
        ArrayList<String> temp= new ArrayList<String>();

        LinearLayout linearLayout =(LinearLayout) findViewById(layout);


        for(int i=0; i<linearLayout.getChildCount();i++){
            EditText editText =(EditText) linearLayout.getChildAt(i);
            temp.add(editText.getText().toString());
        }

        return temp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_edit, menu);


        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
