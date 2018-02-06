package com.example.immortal.multiscreen;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
    String[] activities = {"Лабораторна 4-5", "Вгадай число", "Вибір фону", "Приклади ProgressBar",
            "GoogleReSearch", "Photo Slider", "Guess Flag", "Adress Book"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities);
        setListAdapter(adapter);

        AdapterView.OnItemClickListener ItemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, FourLab.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, GuessNumber.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, PickBackground.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ProgressBarExamples.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, GoogleReSearch.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, PhotoSlider.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, GuessFlag.class));
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this, AdressBook.class));
                        break;
                }
            }
        };
        getListView().setOnItemClickListener(ItemListener);
    }
}
