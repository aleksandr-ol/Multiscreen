package com.example.immortal.multiscreen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GuessFlag extends AppCompatActivity {
    private static final String TAG = "GuessFlag Activity";

    private List<String> fileNameList;
    private List<String> quizCountriesList;
    private Map<String, Boolean> regionsMap;
    private String correctAnswer;
    private int totalGuesses;
    private int correctAnswers;
    private int guessRows;
    private Random random;
    private Handler handler;
    private Animation shakeAnimation;
    private TextView answerTextView;
    private TextView questionNumberTextView;
    private ImageView flagImageView;
    private TableLayout buttonTableLayout;

    private final int CHOISES_MENU_ID = Menu.FIRST;
    private final int REGIONS_MENU_ID = Menu.FIRST + 1;
    private final int BACK_MENU_ID = Menu.FIRST + 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_flag);

        fileNameList = new ArrayList<String>();
        quizCountriesList = new ArrayList<String>();
        regionsMap = new HashMap<String, Boolean>();
        guessRows = 1;
        random = new Random();
        handler = new Handler();

        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3);

        String[] regionNames = getResources().getStringArray(R.array.regionslist);
        for (String region : regionNames)
            regionsMap.put(region, true);

        questionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        flagImageView = (ImageView) findViewById(R.id.flagImageView);
        buttonTableLayout = (TableLayout) findViewById(R.id.buttonTableLayout);

        questionNumberTextView.setText(getResources().getText(R.string.question) + " 1 " +
                getResources().getText(R.string.of) + " 10");

        resetQuiz();
    }

    private void resetQuiz() {
        AssetManager assets = getApplicationContext().getAssets();
        fileNameList.clear();

        try {
            Set<String> regions = regionsMap.keySet();

            for (String region : regions) {
                if (regionsMap.get(region)) {
                    String[] paths = assets.list(region);
                    for (String path : paths)
                        fileNameList.add(path.replace(".png", ""));
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error loading image filenames", e);
        }

        correctAnswers = 0;
        totalGuesses = 0;
        quizCountriesList.clear();

        int flagCounter = 1;
        int numberOfFlags = fileNameList.size();

        while (flagCounter <= 10) {
            int randomIndex = random.nextInt(numberOfFlags);

            String fileName = fileNameList.get(randomIndex);
            if (!quizCountriesList.contains(fileName)) {
                quizCountriesList.add(fileName);
                ++flagCounter;
            }
        }

        loadNextFlag();
    }

    private void loadNextFlag() {
        String nextImagename = quizCountriesList.remove(0);
        correctAnswer = nextImagename;
        answerTextView.setText("");

        questionNumberTextView.setText(getResources().getString(R.string.question) + " " + (correctAnswers + 1) + " "
                + getResources().getString(R.string.of) + " 10");

        String region = nextImagename.substring(0, nextImagename.indexOf('-'));

        AssetManager assets = getApplicationContext().getAssets();
        InputStream stream;
        try {
            stream = assets.open(region + "/" + nextImagename + ".png");
            Drawable flag = Drawable.createFromStream(stream, nextImagename);
            flagImageView.setImageDrawable(flag);
        } catch (IOException e) {
            Log.e(TAG, "Error loading " + nextImagename, e);
        }

        for (int row = 0; row < buttonTableLayout.getChildCount(); ++row)
            ((TableRow) buttonTableLayout.getChildAt(row)).removeAllViews();

        Collections.shuffle(fileNameList);
        int correct = fileNameList.indexOf(correctAnswer);
        fileNameList.add(fileNameList.remove(correct));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int row = 0; row < guessRows; row++) {
            //buttonTableLayout.addView(new TableRow(this));
            TableRow currentTableRow = getTableRow(row);
            for (int column = 0; column < 3; column++) {
                Button newGuessButton = (Button) inflater.inflate(R.layout.guess_button, null);
                String fileName = fileNameList.get((row * 3) + column);
                newGuessButton.setText(getCountryName(fileName));

                newGuessButton.setOnClickListener(guessButtonListener);
                currentTableRow.addView(newGuessButton);

            }
        }

        int row = random.nextInt(guessRows);
        int column = random.nextInt(3);
        TableRow randomtableRow = getTableRow(row);
        String countryName = getCountryName(correctAnswer);
        ((Button) randomtableRow.getChildAt(column)).setText(countryName);
    }

    private TableRow getTableRow(int row) {
        return (TableRow) buttonTableLayout.getChildAt(row);
    }

    private String getCountryName(String name) {
        return name.substring(name.indexOf("-") + 1).replace("_", " ");
    }

    private void submitGuess(Button guessButton) {
        String guess = guessButton.getText().toString();
        String answer = getCountryName(correctAnswer);
        ++totalGuesses;

        if (guess.equals(answer)) {
            ++correctAnswers;
            answerTextView.setText(answer + "!");
            answerTextView.setTextColor(getResources().getColor(R.color.correct_answer));
            //disableButtons();

            if (correctAnswers == 10) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.reset_quiz);

                builder.setMessage(String.format(Locale.getDefault(), "%d %s, %.02f%% %s",
                        totalGuesses, getResources().getString(R.string.guesses),
                        (1000 / (double) totalGuesses), getResources().getString(R.string.correct)));

                builder.setCancelable(false);

                builder.setPositiveButton(R.string.reset_quiz, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetQuiz();
                    }
                });

                AlertDialog resetDialog = builder.create();
                resetDialog.show();
            } else { // наступне питання
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextFlag();
                    }
                }, 1000);
            }
        } else { // Обрано неправильний варіант відповіді
            flagImageView.startAnimation(shakeAnimation);

            answerTextView.setText(R.string.incorrect_answer);
            answerTextView.setTextColor(getResources().getColor(R.color.incorrect_answer));
            guessButton.setEnabled(false);
        }
    }

    private void disableButtons() {
        for (int row = 0; row < buttonTableLayout.getChildCount(); ++row) {
            TableRow tableRow = (TableRow) buttonTableLayout.getChildAt(row);
            for (int i = 0; i < 3; ++i)
                ((Button) tableRow.getChildAt(i)).setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, CHOISES_MENU_ID, Menu.NONE, R.string.choices);
        menu.add(Menu.NONE, REGIONS_MENU_ID, Menu.NONE, R.string.regions);
        menu.add(Menu.NONE, BACK_MENU_ID, Menu.NONE, R.string.back).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case CHOISES_MENU_ID:
                final String[] possibleChoices = getResources().getStringArray(R.array.guesseslist);
                AlertDialog.Builder choicesBuilder = new AlertDialog.Builder(this);
                choicesBuilder.setTitle(R.string.choices);

                choicesBuilder.setItems(R.array.guesseslist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        guessRows = Integer.parseInt(possibleChoices[which]) / 3; // розібратись з toString()
                        resetQuiz();
                    }
                });

                AlertDialog choicesDialog = choicesBuilder.create();
                choicesDialog.show();
                return true;

            case REGIONS_MENU_ID:
                final String[] regionNames = regionsMap.keySet().
                        toArray(new String[regionsMap.size()]);

                boolean[] regionsEnabled = new boolean[regionsMap.size()];
                for (int i = 0; i < regionsEnabled.length; ++i)
                    regionsEnabled[i] = regionsMap.get(regionNames[i]);

                AlertDialog.Builder regionsBuilder = new AlertDialog.Builder(this);
                regionsBuilder.setTitle(R.string.regions);

                String[] displayNames = new String[regionNames.length];
                for (int i = 0; i < regionNames.length; ++i)
                    displayNames[i] = regionNames[i].replace("_", " ");

                regionsBuilder.setMultiChoiceItems(displayNames, regionsEnabled,
                        new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        regionsMap.put(regionNames[which], isChecked);// та же хрінь із toString
                    }
                });

                regionsBuilder.setPositiveButton(R.string.reset_quiz, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetQuiz();
                    }
                });

                AlertDialog regionsDialog = regionsBuilder.create();
                regionsDialog.show();
                return true;
            case BACK_MENU_ID:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View.OnClickListener guessButtonListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitGuess((Button) v);
        }
    };
}
