package com.example.immortal.multiscreen;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarFragment extends Fragment {
    private ProgressBar progress;
    private TextView text;
    private Button buttonStart;
    private Button buttonStop;
    private boolean isRunning = false;

    Context context;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progress.incrementProgressBy(1);
            text.setText("Прогрес: " + progress.getProgress() + " %");
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_progress_bar_fragment, container, false);
        context = view.getContext();

        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        text = (TextView) view.findViewById(R.id.text);
        buttonStart = (Button) view.findViewById(R.id.butto_start);
        buttonStop = (Button) view.findViewById(R.id.button_stop);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.butto_start:
                        onStart();
                        break;
                    case R.id.button_stop:
                        onStop();
                        break;
                }
            }
        };

        buttonStart.setOnClickListener(listener);
        buttonStop.setOnClickListener(listener);

        return view;
    }

    public void onStart(){
        super.onStart();
        progress.setProgress(0);

        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning){
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e){
                        Log.e("ERROR", "Процес зупинено");
                    }
                    handler.sendMessage(handler.obtainMessage());
                }
            }
        });
        isRunning = true;
        background.start();
    }

    public void onStop(){
        super.onStop();
        isRunning = false;
    }
}
