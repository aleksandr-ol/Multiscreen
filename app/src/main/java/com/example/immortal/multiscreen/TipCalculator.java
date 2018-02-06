package com.example.immortal.multiscreen;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class TipCalculator extends Fragment {
    private Context context;

    private static final String BILL_TOTAL = "BILL_TOTAL";
    private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

    private double currentbilltotal;
    private int currentcustompercent;
    private EditText tip10Edittext;
    private EditText total10Edittext;
    private EditText tip15Edittext;
    private EditText total15Edittext;
    private EditText billedittext;
    private EditText tip20Edittext;
    private EditText total20Edittext;
    private TextView customtiptextview;
    private EditText tipcustomedittext;
    private EditText totalcustomedittext;
    private SeekBar customSeekBar;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        if(savedInstanceState != null){
//            currentbilltotal = savedInstanceState.getDouble(BILL_TOTAL);
//            currentcustompercent = savedInstanceState.getInt(CUSTOM_PERCENT);
//
//            updateBill();
//            updateCustomBill();
//        }else{
//            currentbilltotal = 0.0;
//            currentcustompercent = 18;
//        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tip_calculator, container, false);
        context = view.getContext();

        tip10Edittext = (EditText) view.findViewById(R.id.tip10EditText);
        tip15Edittext = (EditText) view.findViewById(R.id.tip15EditText);
        tip20Edittext = (EditText) view.findViewById(R.id.tip20EditText);
        total10Edittext = (EditText) view.findViewById(R.id.total10EditText);
        total15Edittext = (EditText) view.findViewById(R.id.total15EditText);
        total20Edittext = (EditText) view.findViewById(R.id.total20EditText);
        billedittext = (EditText) view.findViewById(R.id.billEditText);
        customtiptextview = (TextView) view.findViewById(R.id.customTipTextView);
        tipcustomedittext = (EditText) view.findViewById(R.id.tipCustomEditText);
        totalcustomedittext = (EditText) view.findViewById(R.id.totalCustomEditText);
        customSeekBar = (SeekBar) view.findViewById(R.id.customSeekBar);

        billedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    currentbilltotal = Double.parseDouble(s.toString());
                } catch (NumberFormatException e) {
                    currentbilltotal = 0.0;
                }
                updateBill();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentcustompercent = seekBar.getProgress();
                updateCustomBill();
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

    protected void updateBill() {
        double tenPercentTip = currentbilltotal * 0.1;
        double tenPercentTotal = currentbilltotal + tenPercentTip;

        tip10Edittext.setText(String.format(" %.02f", tenPercentTip));
        total10Edittext.setText(String.format(" %.02f", tenPercentTotal));

        double fifteenPercentTip = currentbilltotal * 0.15;
        double fifteenPercentTotal = currentbilltotal + fifteenPercentTip;

        tip15Edittext.setText(String.format(" %.02f", fifteenPercentTip));
        total15Edittext.setText(String.format(" %.02f", fifteenPercentTotal));

        double twentyPercentTip = currentbilltotal * 0.20;
        double twentyPercentTotal = currentbilltotal + twentyPercentTip;

        tip20Edittext.setText(String.format(" %.02f", twentyPercentTip));
        total20Edittext.setText(String.format(" %.02f", twentyPercentTotal));
    }

    protected void updateCustomBill() {
        double currentPercentTip = currentbilltotal * currentcustompercent * 0.01;
        double currentPercentTotal = currentbilltotal + currentPercentTip;

        customtiptextview.setText(currentcustompercent + " %");
        tipcustomedittext.setText(String.format(" %.02f", currentPercentTip));
        totalcustomedittext.setText(String.format(" %.02f", currentPercentTotal));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(BILL_TOTAL, currentbilltotal);
        outState.putInt(CUSTOM_PERCENT, currentcustompercent);
    }
}
