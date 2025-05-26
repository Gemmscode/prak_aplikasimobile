package com.example.gemskalkulator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView display;
    private final StringBuilder current = new StringBuilder();
    private Double operand1 = null;
    private String pendingOp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);

        int[] btnIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
                R.id.btn_8, R.id.btn_9, R.id.btn_dot,
                R.id.btn_clear, R.id.btn_sign, R.id.btn_percent,
                R.id.btn_divide, R.id.btn_multiply, R.id.btn_minus,
                R.id.btn_plus, R.id.btn_equals
        };

        for (int id : btnIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String btnText = ((Button) v).getText().toString();
        int id = v.getId();
        if (id == R.id.btn_clear) {
            current.setLength(0);
            operand1 = null;
            pendingOp = null;
            display.setText("0");
        } else if (id == R.id.btn_sign) {
            if (current.length() > 0) {
                if (current.charAt(0) == '-') current.deleteCharAt(0);
                else current.insert(0, '-');
                display.setText(current);
            }
        } else if (id == R.id.btn_percent) {
            if (current.length() > 0) {
                double val = Double.parseDouble(current.toString()) / 100;
                current.setLength(0);
                current.append(val);
                display.setText(current);
            }
        } else if (id == R.id.btn_equals || id == R.id.btn_divide || id == R.id.btn_multiply ||
                id == R.id.btn_minus || id == R.id.btn_plus) {
            performOp(btnText);
        } else {
            // Angka dan titik
            if (btnText.equals(".") && current.toString().contains(".")) return;
            current.append(btnText);
            display.setText(current);
        }
    }

    @SuppressLint("SetTextI18n")
    private void performOp(String op) {
        if (pendingOp != null && current.length() > 0) {
            double val2 = Double.parseDouble(current.toString());
            switch (pendingOp) {
                case "+": operand1 += val2; break;
                case "-": operand1 -= val2; break;
                case "×": operand1 *= val2; break;
                case "÷": operand1 = val2 != 0 ? operand1 / val2 : 0; break;
            }
            display.setText(operand1.toString());
            current.setLength(0);
        } else if (current.length() > 0) {
            operand1 = Double.parseDouble(current.toString());
            current.setLength(0);
        }
        pendingOp = op.equals("=") ? null : op;
    }
}