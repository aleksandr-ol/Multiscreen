package com.example.immortal.multiscreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewContact extends AppCompatActivity {
    private long rowid;
    private TextView nametextview;
    private TextView phonetextview;
    private TextView emailtextview;
    private TextView streettextview;
    private TextView citytextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        nametextview = (TextView) findViewById(R.id.nameTextView);
        phonetextview = (TextView) findViewById(R.id.phoneTextView);
        emailtextview = (TextView) findViewById(R.id.emailTextView);
        streettextview = (TextView) findViewById(R.id.streetTextView);
        citytextview = (TextView) findViewById(R.id.cityTextView);

        Bundle extras = getIntent().getExtras();
        rowid = extras.getLong("row_id");
    }

    @Override
    protected void onResume() {
        super.onResume();

        new LoadContactTask().execute(rowid);
    }

    private class LoadContactTask extends AsyncTask<Long, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(ViewContact.this);

        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.open();
            return databaseConnector.getOneContact(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            cursor.moveToFirst();

            int nameindex = cursor.getColumnIndex("name");
            int phoneindex = cursor.getColumnIndex("phone");
            int emailindex = cursor.getColumnIndex("email");
            int streetindex = cursor.getColumnIndex("street");
            int cityindex = cursor.getColumnIndex("city");

            nametextview.setText(cursor.getString(nameindex));
            phonetextview.setText(cursor.getString(phoneindex));
            emailtextview.setText(cursor.getString(emailindex));
            streettextview.setText(cursor.getString(streetindex));
            citytextview.setText(cursor.getString(cityindex));

            cursor.close();
            databaseConnector.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.view_contact_menu, menu);
        menu.add(0,100,0,"Back").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edititem:
                Intent addEditContact = new Intent(this, AddEditContact.class);
                addEditContact.putExtra("row_id", rowid);
                addEditContact.putExtra("name", nametextview.getText());
                addEditContact.putExtra("phone", phonetextview.getText());
                addEditContact.putExtra("email", emailtextview.getText());
                addEditContact.putExtra("street", streettextview.getText());
                addEditContact.putExtra("city", citytextview.getText());
                startActivity(addEditContact);
                return true;
            case R.id.deleteitem:
                deleteContact();
                return true;
            case 100:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewContact.this);
        builder.setTitle(R.string.confirmtitle);
        builder.setMessage(R.string.confirmmessage_contact_delete);
        builder.setPositiveButton(R.string.button_delete_contact, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final DatabaseConnector databaseConnector = new DatabaseConnector(ViewContact.this);
                AsyncTask<Long, Object, Object> deleteTask = new AsyncTask<Long, Object, Object>() {
                    @Override
                    protected Object doInBackground(Long... params) {
                        databaseConnector.deleteContact(params[0]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        finish();
                    }
                };
                deleteTask.execute(new Long[]{rowid});
            }
        });

        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
}
