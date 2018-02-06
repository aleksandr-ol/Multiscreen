package com.example.immortal.multiscreen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PickBackground extends AppCompatActivity {
    private TextView textView, tvColor, tvSize;
    private CheckBox checkBox;
    private Button bgbutton;
    public LinearLayout linearLayout;
    Context context;

    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_GREEN = 2;
    final int MENU_COLOR_BLUE = 3;

    final int MENU_SIZE_22 = 4;
    final int MENU_SIZE_26 = 5;
    final int MENU_SIZE_30 = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_background);

        bgbutton = (Button) findViewById(R.id.bgbutton);
        textView = (TextView) findViewById(R.id.menu_textview);
        tvColor = (TextView) findViewById(R.id.tvColor);
        tvSize = (TextView) findViewById(R.id.tvSize);
        checkBox = (CheckBox) findViewById(R.id.chbextmenu);

        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);

        linearLayout = (LinearLayout) findViewById(R.id.pickbglayout);
        context = PickBackground.this;

        bgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {getText(R.string.red),
                        getText(R.string.yellow), getText(R.string.green)};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.message_bg);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                linearLayout.setBackgroundResource(R.color.red);
                                Toast.makeText(context, R.string.red, Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                linearLayout.setBackgroundResource(R.color.yellow);
                                Toast.makeText(context, R.string.yellow, Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                linearLayout.setBackgroundResource(R.color.green);
                                Toast.makeText(context, R.string.green, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.tvColor:
                menu.add(0, MENU_COLOR_RED, 0, "RED");
                menu.add(0, MENU_COLOR_GREEN, 0, "Green");
                menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
                break;
            case R.id.tvSize:
                menu.add(0, MENU_SIZE_22, 0, "22");
                menu.add(0, MENU_SIZE_26, 0, "26");
                menu.add(0, MENU_SIZE_30, 0, "30");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_COLOR_RED:
                tvColor.setTextColor(Color.RED);
                tvColor.setText("Колір тексту = червоний");
                return true;
            case MENU_COLOR_GREEN:
                tvColor.setTextColor(Color.GREEN);
                tvColor.setText("Колір тексту = зелений");
                return true;
            case MENU_COLOR_BLUE:
                tvColor.setTextColor(Color.BLUE);
                tvColor.setText("Колір тексту = синій");
                return true;
            case MENU_SIZE_22:
                tvSize.setTextSize(22);
                tvSize.setText("Розмір тексту = 22");
                return true;
            case MENU_SIZE_26:
                tvSize.setTextSize(26);
                tvSize.setText("Розмір тексту = 26");
                return true;
            case MENU_SIZE_30:
                tvSize.setTextSize(30);
                tvSize.setText("Розмір тексту = 30");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.menu_group, checkBox.isChecked());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Item menu");
        stringBuilder.append("\r\n group id: " + String.valueOf(item.getGroupId()));
        stringBuilder.append("\r\n item id: " + String.valueOf(item.getItemId()));
        stringBuilder.append("\r\n order: " + String.valueOf(item.getOrder()));
        stringBuilder.append("\r\n title: " + String.valueOf(item.getTitle()));
        textView.setText(stringBuilder.toString());

        switch (id) {
            case R.id.menu_bookmark:
                Toast.makeText(PickBackground.this, "Закладки обрані", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_save:
                Toast.makeText(PickBackground.this, "Збереження обрано", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_search:
                Toast.makeText(PickBackground.this, "Пошук обраний", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_share:
                Toast.makeText(PickBackground.this, "Відправлення обране", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_delete:
                Toast.makeText(PickBackground.this, "Видалення обрано обрані", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_preferences:
                Toast.makeText(PickBackground.this, "Властивості обрано", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
