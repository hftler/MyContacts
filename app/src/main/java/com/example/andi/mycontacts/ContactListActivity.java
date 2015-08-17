package com.example.andi.mycontacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ListView mListView;

    private String[] testPreNames = {"Andreas", "Matt", "Max", "Peter", "Julius"};
    private String[] testNames = {"Gehring", "Kraus", "Parker", "Fest", "Stadelmann"};
    private ContactList mContacts;
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        /**
         * Test data
         */
        mContacts = ContactList.getInstance();
        for(int i=0; i<20; i++){
            Contact contact1 = new Contact();
            ArrayList<String> emails = new ArrayList<String>();
            emails.add("andreasgehring@microsoft.com");
            emails.add("andreadddddsgehring@microsoft.com");
            emails.add("andreasgetfgffehring@microsoft.com");
            contact1.setEmails(emails);
            ArrayList<String> numbers = new ArrayList<String>();
            numbers.add("09524 5747");
            numbers.add("09524 574567667");
            numbers.add("09524 57465577");
            contact1.setNumbers(numbers);
            int pre_name = (int)(Math.random()*4);
            int last_name = (int)(Math.random()*4);
            contact1.setName(testPreNames[pre_name]+" "+testNames[last_name]);
            mContacts.add(contact1);

        }


        //find ListView, setAdapter and onClickListener
        mListView = (ListView) findViewById(R.id.contact_list);
        mAdapter = new ContactAdapter(mContacts);
        mListView.setAdapter(mAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            int firstPreviousItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            //hide the ActionBar if scroll down, show it when you scroll up
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > firstPreviousItem) {
                    getSupportActionBar().hide();
                } else if (firstPreviousItem > firstVisibleItem) {
                    getSupportActionBar().show();
                }

                firstPreviousItem = firstVisibleItem;
            }
        });


        //setOnItemClickListener and start ContactViewActivity on the clicked Item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(ContactListActivity.this, ContactViewActivity.class);
                intent.putExtra(ContactViewActivity.EXTRA, position);
                startActivity(intent);


            }
        });
    }


    /**
     * ContactAdapter
     */
    private class ContactAdapter extends ArrayAdapter<Contact>{
        ContactAdapter(ArrayList<Contact> contacts){
            super(ContactListActivity.this, R.layout.contact_list_row,R.id.contact_row_name, contacts);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //initialize convertView
            convertView = super.getView(position,convertView,parent);
           

            //get selected contact
            Contact contact = getItem(position);

            //find TextView and rename it
            TextView nameView = (TextView) convertView.findViewById(R.id.contact_row_name);
            nameView.setText(contact.getName());

            return convertView;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
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
