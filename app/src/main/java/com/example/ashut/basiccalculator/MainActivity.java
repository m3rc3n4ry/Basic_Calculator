package com.example.ashut.basiccalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private int[] numericButtons = {R.id.btnZero, R.id.btnOne, R.id.btnTwo,
    R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};

    private int operatorButtons[] = {R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnClr};

    // creating three booleans
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastDot;

    // creating TextView
    private TextView txtScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating 'this' pointer
        this.txtScreen = findViewById(R.id.txtScreen);

        // assigning onClickListener
        setNumericOnClickListener();
        setOperatorOnClickListener();
    }

    private void setNumericOnClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;

                // checking error
                if(stateError){
                    txtScreen.setText(button.getText());
                    stateError = false;
                }
                else{
                    txtScreen.append(button.getText());
                }
                lastNumeric = true;  // because in Java it's false by default
            }
        };

        // adding for-each loop
        for(int id:numericButtons){
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorOnClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastNumeric && !stateError){                         // last digit needs to be a
                                                                        // number and nothing else
                    Button button = (Button) v;
                    txtScreen.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;
                }
            }
        };
        for(int id:operatorButtons){
            findViewById(id).setOnClickListener(listener);
        }

        // for decimal
        findViewById(R.id.btnPoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastNumeric && !stateError && !lastDot){             // last digit needs to be a
                                                                        // number and nothing else
                    txtScreen.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });

        // for clear
        findViewById(R.id.btnClr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScreen.setText(" ");
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });

        // for equal
        findViewById(R.id.btnEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    private void onEqual(){
        if(lastNumeric && !stateError){

            String txt = txtScreen.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();

            // try-catch block
            try{
                double result = expression.evaluate();
                txtScreen.setText(Double.toString(result));     // here Double is a Java class
                lastDot = true;      // to put a decimal at the end
            }
            catch (ArithmeticException ex){
                txtScreen.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }
}
