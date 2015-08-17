package com.example.andi.mycontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactViewActivity extends AppCompatActivity {

    public static final String EXTRA = "Serialized_contact";
    private Contact mContact;
    private TextView mContactNameView;
    private ListView mContactViewData;
    private int mColor;
    private  int mPosition;
    private FieldsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        //get the Display and the width and height
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;

        //make the header (image+name of contact) 16:9
        RelativeLayout header = (RelativeLayout) findViewById(R.id.contact_view_header);
        header.setLayoutParams(new LinearLayout.LayoutParams(width,(int) (width*(9.0/16.0))));


        //set the name
        mContactNameView = (TextView) findViewById(R.id.contact_view_name);

        mPosition = getIntent().getIntExtra(EXTRA,0);
        mContact = ContactList.getInstance().get(mPosition);
        mContactNameView.setText(mContact.getName());

        //inflate a toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_view_toolbar);
       toolbar.inflateMenu(R.menu.menu_contact_view);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuID = item.getItemId();
                if (menuID == R.id.contact_view_edit) {
                    Intent intent = new Intent(ContactViewActivity.this, ContactEditActivity.class);
                    intent.putExtra(ContactEditActivity.EDIT_CONTACT, mPosition);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });

        mContactViewData = (ListView) findViewById(R.id.contact_view_fields);
        mAdapter = new FieldsAdapter(mContact.getNumbers(), mContact.getEmails());
        mContactViewData.setAdapter(mAdapter);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beach);
        Palette palette = Palette.generate(bitmap);
        mColor = palette.getDarkVibrantSwatch().getRgb();
    }

    //implement a BaseAdapter for ListView
    private class FieldsAdapter extends BaseAdapter{
        protected ArrayList<String> phoneNumbers;
        protected ArrayList<String> emails;
        FieldsAdapter(ArrayList<String> phoneNumbers, ArrayList<String> emails){
            this.phoneNumbers = phoneNumbers;
            this.emails = emails;
        }

        @Override
        public int getCount() {
            return phoneNumbers.size()+emails.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //if convertView == null instantiate it
            if(convertView == null){
                convertView = ContactViewActivity.this.getLayoutInflater().inflate(R.layout.contact_field_row, parent, false);
            }

            String value = (String) getItem(position);
            TextView contactValue = (TextView)convertView.findViewById(R.id.contact_view_row_value);
            contactValue.setText(value);
           setImage(position, convertView);
            return convertView;
        }

    public void setImage(int position, View convertView){
        ImageView imageView = (ImageView) convertView.findViewById(R.id.contact_view_image_data);
        if(position == 0){
            imageView.setImageResource(R.mipmap.ic_mail);
        }else if(position == emails.size()){
            imageView.setImageResource(R.mipmap.ic_call);
        }
        imageView.setColorFilter(mColor);

    }

        @Override
        public Object getItem(int position) {
            if(position < emails.size()){
                return emails.get(position);
            }else{
                return phoneNumbers.get(position-emails.size());
            }
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    private void updateUI(){
        mContactNameView.setText(mContact.getName());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_view, menu);
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
