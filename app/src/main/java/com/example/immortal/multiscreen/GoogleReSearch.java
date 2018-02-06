package com.example.immortal.multiscreen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Arrays;

public class GoogleReSearch extends AppCompatActivity {
    private SharedPreferences savedSearches;

    private TableLayout queryTableLayout;
    private EditText queryEditText;
    private EditText tagEditText;
    private Button saveButton;
    private Button clearTagsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_re_search);

        savedSearches = getSharedPreferences("searches", MODE_PRIVATE);

        queryTableLayout = (TableLayout) findViewById(R.id.queryTableLayout);
        tagEditText = (EditText) findViewById(R.id.tagEditText);
        queryEditText = (EditText) findViewById(R.id.queryEditText);
        saveButton = (Button) findViewById(R.id.saveButton);
        clearTagsButton = (Button) findViewById(R.id.clearTagsButton);

        saveButton.setOnClickListener(saveButtonListener);
        clearTagsButton.setOnClickListener(clearTagsButtonListener);

        refreshButtons(null);
    }

    private void refreshButtons(String newTag) {
        String[] tags = savedSearches.getAll().keySet().toArray(new String[0]);
        Arrays.sort(tags, String.CASE_INSENSITIVE_ORDER);

        if (newTag != null) {
            makeTagGui(newTag, Arrays.binarySearch(tags, newTag));
        } else {
            for (int i = 0; i < tags.length; ++i) {
                makeTagGui(tags[i], i);
            }
        }
    }

    private void makeTag(String query, String tag) {
        String originalQuery = savedSearches.getString(tag, null);

        SharedPreferences.Editor preferencesEditor = savedSearches.edit();
        preferencesEditor.putString(tag, query);
        preferencesEditor.apply();

        if (originalQuery == null)
            refreshButtons(tag);
    }

    private void makeTagGui(String tag, int index) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newTagView = inflater.inflate(R.layout.new_tag_table_row, null);

        Button newTagButton = (Button) newTagView.findViewById(R.id.newtagbutton);
        newTagButton.setText(tag);
        newTagButton.setOnClickListener(queryButtonListener);

        Button newEditButton = (Button) newTagView.findViewById(R.id.neweditbutton);
        newEditButton.setOnClickListener(editButtonListener);

        queryTableLayout.addView(newTagView, index);
    }

    private void clearButtons() {
        queryTableLayout.removeAllViews();
    }


    public View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (queryEditText.getText().length() > 0 && tagEditText.getText().length() > 0) {
                makeTag(queryEditText.getText().toString(), tagEditText.getText().toString());

                queryEditText.setText("");
                tagEditText.setText("");
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(tagEditText.getWindowToken(), 0);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(GoogleReSearch.this);
                builder.setTitle(R.string.missingtitle);
                builder.setPositiveButton(R.string.OK, null);
                builder.setMessage(R.string.missingmessage);

                AlertDialog errorDialog = builder.create();
                errorDialog.show();
            }
        }
    };


    public View.OnClickListener clearTagsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GoogleReSearch.this);
            builder.setTitle(R.string.confirmtitle);

            builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    clearButtons();
                    SharedPreferences.Editor preferencesEditor = savedSearches.edit();
                    preferencesEditor.clear();
                    preferencesEditor.apply();
                }
            });

            builder.setCancelable(true);
            builder.setNegativeButton(R.string.cancel, null);
            builder.setMessage(R.string.confirmmessage);

            AlertDialog errorDialog = builder.create();
            errorDialog.show();
        }
    };


    public View.OnClickListener queryButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String buttonText = ((Button) v).getText().toString();
            String query = savedSearches.getString(buttonText, null);
            String urlString = getString(R.string.searchurl) + query;

            Intent getUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            startActivity(getUrl);
        }
    };


    public View.OnClickListener editButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TableRow buttonTableRow = (TableRow) v.getParent();
            Button searchButton = (Button) buttonTableRow.findViewById(R.id.newtagbutton);

            String tag = searchButton.getText().toString();
            tagEditText.setText(tag);
            queryEditText.setText(savedSearches.getString(tag, null));
        }
    };
}
