package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private String pendingOperation = "=";

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonClear = findViewById(R.id.buttonClear);

        @SuppressLint({"MissingInflatedId", "LocalSuppress", "CutPasteId"}) Button buttonNegate = findViewById(R.id.buttonClear);

        View.OnClickListener listener = view -> {
            Button b = (Button) view;
            newNumber.append(b.getText().toString());
        };
        Button[] buttons = {button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonDot};
        for (Button button : buttons) {
            button.setOnClickListener(listener);
        }

        View.OnClickListener opListener = view -> {
            Button b = (Button) view;
            String op = b.getText().toString();
            String value = newNumber.getText().toString();
            try {
                Double doubleValue = Double.valueOf(value);
                performOperation(doubleValue, op);
            } catch (NumberFormatException e) {
                newNumber.setText("");
            }
            pendingOperation = op;
            displayOperation.setText(pendingOperation);
        };
        Button[] buttonOp = {buttonEquals, buttonAdd, buttonMinus, buttonMultiply, buttonDivide};
        for (Button button : buttonOp) {
            button.setOnClickListener(opListener);
        }

        View.OnClickListener negateListener = view -> {
            String value = newNumber.getText().toString();
            if (value.length() == 0) {
                newNumber.setText("-");
            } else {
                try {
                    Double doubleValue = Double.valueOf(value);
                    doubleValue *= -1;
                    newNumber.setText(doubleValue.toString());
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
            }
        };

        buttonNegate.setOnClickListener(negateListener);

        buttonClear.setOnClickListener(view -> {
            String value = newNumber.getText().toString();
            if (pendingOperation != null) {
                displayOperation.setText("");
                operand1 = null;
                result.setText("");
                if (value.length() != 0) {
                    newNumber.setText("");
                }
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void performOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND);
        displayOperation.setText(pendingOperation);
    }
}


























