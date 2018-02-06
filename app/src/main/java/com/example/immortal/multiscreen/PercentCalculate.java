package com.example.immortal.multiscreen;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class PercentCalculate extends Fragment {
    EditText number;
    SeekBar seekBar;
    TextView result;

    Context context;

    private static final String NUMBER_STATE = "NUMBER";
    private static final String PERCENT_STATE = "PERCENT";

    double current_number = 0.0;
    int current_percent = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState != null) {
//            current_number = savedInstanceState.getDouble(NUMBER_STATE);
//            current_percent = savedInstanceState.getInt(PERCENT_STATE);
//            count_result();
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_percent_calculate, container, false);
        context = view.getContext();

        number = (EditText) view.findViewById(R.id.number);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        result = (TextView) view.findViewById(R.id.result);

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    current_number = Double.parseDouble(s.toString());
                } catch (NumberFormatException e) {
                    current_number = 0.0;
                }
                count_result();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current_percent = seekBar.getProgress();
                count_result();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    protected void count_result() {
        double percent = current_number * current_percent * 0.01;
        result.setText(String.format(Locale.US, current_percent + "%s від %.02f -  %.02f", "%", current_number, percent));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(NUMBER_STATE, current_number);
        outState.putInt(PERCENT_STATE, current_percent);
    }
}
