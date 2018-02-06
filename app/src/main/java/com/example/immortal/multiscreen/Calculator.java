package com.example.immortal.multiscreen;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class Calculator extends Fragment {
    EditText editText;
    Button count;
    Button divide;
    Button multiply;
    Button minus;
    Button plus;
    Button square;
    Button elevate;

    double first_number = 0.0;
    double second_number = 0.0;
    double result = 0.0;
    String operation = "-";

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
        final View view = inflater.inflate(R.layout.activity_calculator, container, false);
        context = view.getContext();

        count = (Button) view.findViewById(R.id.count);
        divide = (Button) view.findViewById(R.id.divide);
        multiply = (Button) view.findViewById(R.id.multiply);
        minus = (Button) view.findViewById(R.id.minus);
        plus = (Button) view.findViewById(R.id.plus);
        square = (Button) view.findViewById(R.id.square);
        elevate = (Button) view.findViewById(R.id.elevate);

        editText = (EditText) view.findViewById(R.id.editText);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.minus:
                        operation = "-";
                        first_number = Double.parseDouble(editText.getText().toString());
                        editText.setText("");
                        break;
                    case R.id.divide:
                        operation = "/";
                        first_number = Double.parseDouble(editText.getText().toString());
                        editText.setText("");
                        break;
                    case R.id.multiply:
                        operation = "*";
                        first_number = Double.parseDouble(editText.getText().toString());
                        editText.setText("");
                        break;
                    case R.id.plus:
                        operation = "+";
                        first_number = Double.parseDouble(editText.getText().toString());
                        editText.setText("");
                        break;
                    case R.id.square:
                        operation = "√";
                        first_number = Double.parseDouble(editText.getText().toString());
                        if (first_number < 0) {
                            Toast.makeText(context, "Число від'ємне", Toast.LENGTH_LONG).show();
                            break;
                        }
                        result = Math.sqrt(first_number);
                        editText.setText(String.format(Locale.US, "%.02f", result));
                        break;
                    case R.id.elevate:
                        operation = "^";
                        first_number = Double.parseDouble(editText.getText().toString());
                        editText.setText("");
                        break;
                    case R.id.count:
                        second_number = Double.parseDouble(editText.getText().toString());
                        switch (operation) {
                            case "+":
                                result = first_number + second_number;
                                editText.setText(String.format(Locale.US, "%.02f", result));
                                break;
                            case "-":
                                result = first_number - second_number;
                                editText.setText(String.format(Locale.US, "%.02f", result));
                                break;
                            case "/":
                                if (second_number == 0) {
                                    Toast.makeText(context, "Ділення на 0", Toast.LENGTH_LONG).show();
                                    break;
                                }
                                result = first_number / second_number;
                                editText.setText(String.format(Locale.US, "%.02f", result));
                                break;
                            case "*":
                                result = first_number * second_number;
                                editText.setText(String.format(Locale.US, "%.02f", result));
                                break;
                            case "^":
                                result = Math.pow(first_number, second_number);
                                editText.setText(String.format(Locale.US, "%.02f", result));
                                break;
                        }
                        break;
                }
            }
        };

        count.setOnClickListener(onClickListener);
        divide.setOnClickListener(onClickListener);
        multiply.setOnClickListener(onClickListener);
        minus.setOnClickListener(onClickListener);
        plus.setOnClickListener(onClickListener);
        square.setOnClickListener(onClickListener);
        elevate.setOnClickListener(onClickListener);

        return view;
    }
}
