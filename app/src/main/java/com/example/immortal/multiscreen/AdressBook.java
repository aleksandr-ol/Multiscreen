package com.example.immortal.multiscreen;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AdressBook extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    public static final String ROW_ID = "row_id";
    private ListView contactListView;
    private SimpleCursorAdapter contactAdapter;
    DatabaseConnector databaseConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_book);


        contactListView = (ListView) findViewById(R.id.adressBook_listView);
        contactListView.setOnItemClickListener(viewContactListener);

        String[] from = new String[]{"name"};
        int[] to = new int[]{R.id.contactTextView};

        databaseConnector = new DatabaseConnector(this);
        databaseConnector.open();

        contactAdapter = new SimpleCursorAdapter(AdressBook.this, R.layout.contact_list_item, null, from, to, 0);
        contactListView.setAdapter(contactAdapter);

        getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseConnector.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, databaseConnector);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        contactAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class MyCursorLoader extends CursorLoader {
        DatabaseConnector db;

        public MyCursorLoader(Context context, DatabaseConnector db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllContacts();
            return cursor;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetContactTask().execute((Object[]) null);
    }

    private class GetContactTask extends AsyncTask<Object, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(AdressBook.this);

        @Override
        protected Cursor doInBackground(Object... params) {
            databaseConnector.open();

            return databaseConnector.getAllContacts();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            contactAdapter.changeCursor(cursor);
            databaseConnector.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.adress_book_menu, menu);
        menu.add(0,100,0,"Back").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addcontactitem:
                Intent addNewContact = new Intent(AdressBook.this, AddEditContact.class);
                startActivity(addNewContact);
                return true;
            case 100:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    AdapterView.OnItemClickListener viewContactListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent viewContact = new Intent(AdressBook.this, ViewContact.class);
            viewContact.putExtra(ROW_ID, id);
            startActivity(viewContact);
        }
    };
}
