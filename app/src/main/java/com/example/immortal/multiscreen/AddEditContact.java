package com.example.immortal.multiscreen;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEditContact extends AppCompatActivity {
    private long rowid;
    private EditText nameedittext;
    private EditText phoneedittext;
    private EditText emailedittext;
    private EditText streetedittext;
    private EditText cityedittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        nameedittext = (EditText) findViewById(R.id.nameedittext);
        phoneedittext = (EditText) findViewById(R.id.phoneedittext);
        emailedittext = (EditText) findViewById(R.id.emailedittext);
        streetedittext = (EditText) findViewById(R.id.streetedittext);
        cityedittext = (EditText) findViewById(R.id.cityedittext);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rowid = extras.getLong("row_id");
            nameedittext.setText(extras.getString("name"));
            phoneedittext.setText(extras.getString("phone"));
            emailedittext.setText(extras.getString("email"));
            streetedittext.setText(extras.getString("street"));
            cityedittext.setText(extras.getString("city"));
        }

        Button saveContactButton = (Button) findViewById(R.id.saveContactButton);
        saveContactButton.setOnClickListener(saveContactButtonClicked);
    }

    View.OnClickListener saveContactButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (nameedittext.getText().length() != 0) {
                AsyncTask<Object, Object, Object> saveContactTask =
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... params) {
                                saveContact();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object o) {
                                finish();
                            }
                        };
                saveContactTask.execute((Object[]) null);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEditContact.this);
                builder.setTitle(R.string.error);
                builder.setMessage(R.string.message);
                builder.setPositiveButton(R.string.OK, null);
                builder.show();
            }
        }
    };

    private void saveContact(){
        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        if (getIntent().getExtras() == null){
            databaseConnector.insertContact(
              nameedittext.getText().toString(),
              phoneedittext.getText().toString(),
              emailedittext.getText().toString(),
              streetedittext.getText().toString(),
              cityedittext.getText().toString()
            );
        } else {
            databaseConnector.updateContact(
                    rowid,
                    nameedittext.getText().toString(),
                    phoneedittext.getText().toString(),
                    emailedittext.getText().toString(),
                    streetedittext.getText().toString(),
                    cityedittext.getText().toString()
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,100,0,"Back").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
