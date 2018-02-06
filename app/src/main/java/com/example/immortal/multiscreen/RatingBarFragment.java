package com.example.immortal.multiscreen;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingBarFragment extends Fragment {
    private static final int NUM_STARS = 5;
    private float step = 0.5f;
    private float rat = 1.0f;
    private RatingBar ratingBar;
    private TextView textView;
    private Button buttonUp;
    private Button buttonDown;

    Context context;

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
        final View view = inflater.inflate(R.layout.activity_rating_bar_fragment, container, false);
        context = view.getContext();

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        textView = (TextView) view.findViewById(R.id.value);
        buttonUp = (Button) view.findViewById(R.id.button_up);
        buttonDown = (Button) view.findViewById(R.id.button_down);

        ratingBar.setNumStars(NUM_STARS);
        ratingBar.setRating(rat);
        ratingBar.setStepSize(step);

        textView.setText("Значення: " + String.valueOf(rat));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_up:
                        rat += step;
                        if(rat > NUM_STARS){
                            rat = NUM_STARS;
                        }
                        ratingBar.setRating(rat);
                        break;
                    case R.id.button_down:
                        rat -= step;
                        if(rat < 0){
                            rat = 0;
                        }
                        ratingBar.setRating(rat);
                        break;
                }
            }
        };

        buttonUp.setOnClickListener(listener);
        buttonDown.setOnClickListener(listener);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textView.setText("Значення: " + String.valueOf(ratingBar.getRating()));
                rat = ratingBar.getRating();
            }
        });

        return view;
    }
}
