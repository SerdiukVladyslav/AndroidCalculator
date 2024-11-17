package com.example.androidcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView displayTextView;
    private StringBuilder inputBuilder = new StringBuilder();
    private double operand1 = 0;
    private String operator = "";
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTextView = findViewById(R.id.displayTextView);

        setNumberButtonListeners();

        setOperatorButtonListeners();

        findViewById(R.id.buttonClear).setOnClickListener(view -> clearDisplay());
        findViewById(R.id.buttonEquals).setOnClickListener(view -> calculateResult());
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(this::onNumberClick);
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtonIds = { R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide };

        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(this::onOperatorClick);
        }
    }

    private void onNumberClick(View view) {
        Button button = (Button) view;
        if (isNewInput) {
            inputBuilder.setLength(0);
            isNewInput = false;
        }
        inputBuilder.append(button.getText().toString());
        displayTextView.setText(inputBuilder.toString());
    }

    private void onOperatorClick(View view) {
        Button button = (Button) view;
        String selectedOperator = button.getText().toString();

        if (!inputBuilder.toString().isEmpty()) {
            operand1 = Double.parseDouble(inputBuilder.toString());
            operator = selectedOperator;
            isNewInput = true;
        }
    }

    private void calculateResult() {
        if (!inputBuilder.toString().isEmpty() && !operator.isEmpty()) {
            double operand2 = Double.parseDouble(inputBuilder.toString());
            double result = 0;

            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    } else {
                        displayTextView.setText("Error");
                        return;
                    }
                    break;
            }

            displayTextView.setText(String.valueOf(result));
            inputBuilder.setLength(0);
            inputBuilder.append(result);
            operator = "";
            isNewInput = true;
        }
    }

    private void clearDisplay() {
        inputBuilder.setLength(0);
        displayTextView.setText("");
        operand1 = 0;
        operator = "";
        isNewInput = true;
    }
}
