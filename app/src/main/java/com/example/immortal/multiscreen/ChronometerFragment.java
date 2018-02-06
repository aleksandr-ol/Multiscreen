package com.example.immortal.multiscreen;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

public class ChronometerFragment extends Fragment {
    private Chronometer chronometer;
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonRestart;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_chronometer, container, false);
        context = view.getContext();

        chronometer = (Chronometer) view.findViewById(R.id.chronometer);
        buttonStart = (Button) view.findViewById(R.id.button_start);
        buttonStop = (Button) view.findViewById(R.id.button_stop);
        buttonRestart = (Button) view.findViewById(R.id.button_restart);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_start:
                        chronometer.start();
                        break;
                    case R.id.button_stop:
                        chronometer.stop();
                        break;
                    case R.id.button_restart:
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        break;
                }
            }
        };

        buttonStart.setOnClickListener(listener);
        buttonStop.setOnClickListener(listener);
        buttonRestart.setOnClickListener(listener);

        return view;
    }
}