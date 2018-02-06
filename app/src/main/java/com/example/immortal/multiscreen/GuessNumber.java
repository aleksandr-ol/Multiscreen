package com.example.immortal.multiscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GuessNumber extends AppCompatActivity {
    TextView textView;
    TextView guesses_count_textView;
    EditText editText;
    Button button;

    int number = (int) (Math.random() * 100);
    boolean gamefinished = false;
    int guesses_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);

        number = 43;

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        guesses_count_textView = (TextView) findViewById(R.id.textView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gamefinished) {
                    int pick;
                    try {
                        pick = Integer.parseInt(editText.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(GuessNumber.this, "Некоректно введене число", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (pick <= 0 || pick > 100) {
                        Toast.makeText(GuessNumber.this, "Введіть число в діапазоні від 1 до 100", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (pick == number) {
                        textView.setText(R.string.success);
                        button.setText(R.string.play_more);
                        gamefinished = true;
                    }
                    if (pick < number) {
                        textView.setText(R.string.below);
                    }
                    if (pick > number) {
                        textView.setText(R.string.after);
                    }
                    guesses_count += 1;
                    guesses_count_textView.setText("Кількість спроб: " + guesses_count);
                } else {
                    number = (int) (Math.random() * 100);
                    button.setText(R.string.pick_btn);
                    textView.setText(R.string.pick_number);
                    gamefinished = false;
                    guesses_count = 0;
                    guesses_count_textView.setText(R.string.guesses_count);
                }
                editText.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item_back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
