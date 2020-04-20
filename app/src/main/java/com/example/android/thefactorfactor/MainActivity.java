package com.example.android.thefactorfactor;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
public class MainActivity<string> extends AppCompatActivity {
    public int Input_Num;
    private LinearLayout linearLayout1;
    private EditText Number;
    private Button Submit;
    private TextView Result;
    private TextView mScoreView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private int mScore;
    private int Hscore;
    private TextView HscoreView;
    private TextView mTextField;
    String str;
    String Correct_Answer;
    CountDownTimer Waitimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HscoreView = (TextView) findViewById(R.id.hs);
        Number = (EditText) findViewById(R.id.question);
        Submit = (Button) findViewById(R.id.button);
        mScoreView = (TextView)findViewById(R.id.score);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        Result = (TextView) findViewById(R.id.Result);
        linearLayout1 =(LinearLayout) findViewById(R.id.ac);
        mTextField=(TextView) findViewById(R.id.time);
        final Vibrator vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
        SharedPreferences myScore = this.getSharedPreferences("MyAwesomeScore", Context.MODE_PRIVATE);
        Hscore = myScore.getInt("score", 0);
        mScoreView.setText("" + 0);
        HscoreView.setText("" + Hscore);
        mButtonChoice1.setEnabled(false);
        mButtonChoice2.setEnabled(false);
        mButtonChoice3.setEnabled(false);
        Submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                mButtonChoice1.setBackgroundColor(Color.BLUE);
                mButtonChoice2.setBackgroundColor(Color.BLUE);
                mButtonChoice3.setBackgroundColor(Color.BLUE);
                linearLayout1.setBackgroundColor(Color.WHITE);
                Result.setText("Result");
                str = Number.getText().toString();
                if (str.matches("")) {
                    Toast.makeText(MainActivity.this, "Enter The Number", Toast.LENGTH_SHORT).show();
                    mButtonChoice1.setEnabled(false);
                    mButtonChoice2.setEnabled(false);
                    mButtonChoice3.setEnabled(false);
                } else {
                    Input_Num = Integer.parseInt(Number.getText().toString());
                    if (Input_Num == 0) {
                        Toast.makeText(MainActivity.this, "0, Dont Have any Factors", Toast.LENGTH_SHORT).show();
                        mButtonChoice1.setEnabled(false);
                        mButtonChoice2.setEnabled(false);
                        mButtonChoice3.setEnabled(false);
                    } else if (Input_Num == 1) {
                        Toast.makeText(MainActivity.this, "1, Have only 1 as its Factors", Toast.LENGTH_SHORT).show();
                        mButtonChoice1.setEnabled(false);
                        mButtonChoice2.setEnabled(false);
                        mButtonChoice3.setEnabled(false);
                    } else {
                        mButtonChoice1.setEnabled(true);
                        mButtonChoice2.setEnabled(true);
                        mButtonChoice3.setEnabled(true);
                        int[] arr;
                        arr = new int[10000000];
                        int[] arr2;
                        arr2 = new int[10000000];
                        int number = Input_Num;
                        int i = 0;
                        int x = 0;
                        for (int loopCounter = 1; loopCounter <= number; loopCounter++) {
                            if (number % loopCounter == 0) {
                                arr[i] = loopCounter;
                                i = i + 1;
                            } else {
                                arr2[x] = loopCounter;
                                x = x + 1;
                            }
                        }
                        int targetIndex = 0;
                        for (int sourceIndex = 0; sourceIndex < arr.length; sourceIndex++) {
                            if (arr[sourceIndex] != 0)
                                arr[targetIndex++] = arr[sourceIndex];
                        }
                        int[] newArr = new int[targetIndex];
                        System.arraycopy(arr, 0, newArr, 0, targetIndex);
                        int rnd = new Random().nextInt(newArr.length);
                        int rnd1 = new Random().nextInt(newArr.length);
                        Correct_Answer = Integer.toString(newArr[rnd1]);
                        String b = Integer.toString(arr2[rnd1]);
                        int rnd2 = new Random().nextInt(arr2.length);
                        while (rnd2 == rnd1) {
                            rnd2 = new Random().nextInt(arr2.length);
                        }
                        String c = Integer.toString(arr2[rnd2]);
                        Random rand = new Random();
                        int n = rand.nextInt(6);
                        if ((n == 0) || (n == 3)) {
                            mButtonChoice1.setText(Correct_Answer);
                            mButtonChoice2.setText(b);
                            mButtonChoice3.setText(c);
                        } else if ((n == 4) || (n == 1)) {
                            mButtonChoice1.setText(c);
                            mButtonChoice2.setText(Correct_Answer);
                            mButtonChoice3.setText(b);
                        } else if ((n == 2) || (n == 5)) {
                            mButtonChoice1.setText(b);
                            mButtonChoice2.setText(c);
                            mButtonChoice3.setText(Correct_Answer);
                        }
                        Waitimer = new CountDownTimer(10000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                mTextField.setText("Timer: " + (millisUntilFinished / 1000));
                            }
                            public void onFinish() {
                                mTextField.setText("Time Up!");
                                vibrator.vibrate(1000);
                                Result.setText("The Correct Answer is " + Correct_Answer);
                                linearLayout1.setBackgroundColor(Color.RED);
                                mButtonChoice1.setEnabled(false);
                                mButtonChoice2.setEnabled(false);
                                mButtonChoice3.setEnabled(false);
                            }
                        }.start();
                    }
                }
            }
        });
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Waitimer.cancel();
                if (mButtonChoice1.getText() == Correct_Answer){
                    vibrator.cancel();
                    mScore = mScore + 1;
                    updateScore(mScore);
                    if (mScore>=Hscore){
                        Hscore=mScore;
                        HscoreView.setText("" + Hscore);
                    }
                    SharedPreferences myScore = getSharedPreferences("MyAwesomeScore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myScore.edit();
                    editor.putInt("score", Hscore);
                    editor.commit();
                    Result.setText("Correct");
                    linearLayout1.setBackgroundColor(Color.GREEN);
                    mButtonChoice1.setBackgroundColor(Color.GREEN);
                    mButtonChoice1.setEnabled(false);
                    mButtonChoice2.setEnabled(false);
                    mButtonChoice3.setEnabled(false);
                }else {
                    Result.setText("Wrong "+ "and The Correct answer: "+ Correct_Answer);
                    linearLayout1.setBackgroundColor(Color.RED);
                    mButtonChoice1.setBackgroundColor(Color.RED);
                    if (mScore>=Hscore){
                        Hscore=mScore;
                        HscoreView.setText("" + Hscore);
                    }
                    SharedPreferences myScore = getSharedPreferences("MyAwesomeScore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myScore.edit();
                    editor.putInt("score", Hscore);
                    editor.commit();
                    mScore=0;
                    vibrator.vibrate(1000);
                    mButtonChoice1.setEnabled(false);
                    mButtonChoice2.setEnabled(false);
                    mButtonChoice3.setEnabled(false);
                }
            }
        });
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Waitimer.cancel();
                if (mButtonChoice2.getText() == Correct_Answer){
                    vibrator.cancel();
                    mScore = mScore + 1;
                    updateScore(mScore);
                    if (mScore>=Hscore){
                        Hscore=mScore;
                        HscoreView.setText("" + Hscore);
                    }
                    SharedPreferences myScore = getSharedPreferences("MyAwesomeScore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myScore.edit();
                    editor.putInt("score", Hscore);
                    editor.commit();
                    Result.setText("Correct");
                    linearLayout1.setBackgroundColor(Color.GREEN);
                    mButtonChoice2.setBackgroundColor(Color.GREEN);
                    mButtonChoice1.setEnabled(false);
                    mButtonChoice2.setEnabled(false);
                    mButtonChoice3.setEnabled(false);
                }else {
                    vibrator.vibrate(1000);
                    Result.setText("Wrong "+ "and The Correct answer: "+ Correct_Answer);
                    linearLayout1.setBackgroundColor(Color.RED);
                    if (mScore>=Hscore){
                        Hscore=mScore;
                        HscoreView.setText("" + Hscore);
                    }
                    SharedPreferences myScore = getSharedPreferences("MyAwesomeScore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myScore.edit();
                    editor.putInt("score", Hscore);
                    editor.commit();
                    mScore=0;
                    mButtonChoice2.setBackgroundColor(Color.RED);
                    mButtonChoice1.setEnabled(false);
                    mButtonChoice2.setEnabled(false);
                    mButtonChoice3.setEnabled(false);
                }
            }
        });
        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Waitimer.cancel();
                if (mButtonChoice3.getText() == Correct_Answer){
                    vibrator.cancel();
                    mScore = mScore + 1;
                    updateScore(mScore);
                    if (mScore>=Hscore){
                        Hscore=mScore;
                        HscoreView.setText("" + Hscore);
                    }
                    SharedPreferences myScore = getSharedPreferences("MyAwesomeScore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myScore.edit();
                    editor.putInt("score", Hscore);
                    editor.commit();
                    Result.setText("Correct");
                    linearLayout1.setBackgroundColor(Color.GREEN);
                    mButtonChoice3.setBackgroundColor(Color.GREEN);
                    mButtonChoice1.setEnabled(false);
                    mButtonChoice2.setEnabled(false);
                    mButtonChoice3.setEnabled(false);
                }else {
                    Result.setText("Wrong "+ "and The Correct answer: "+ Correct_Answer);
                    linearLayout1.setBackgroundColor(Color.RED);
                    mButtonChoice3.setBackgroundColor(Color.RED);
                    vibrator.vibrate(1000);
                    if (mScore>=Hscore){
                        Hscore=mScore;
                        HscoreView.setText("" + Hscore);
                    }
                    SharedPreferences myScore = getSharedPreferences("MyAwesomeScore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myScore.edit();
                    editor.putInt("score", Hscore);
                    editor.commit();
                    mScore=0;
                    mButtonChoice1.setEnabled(false);
                    mButtonChoice2.setEnabled(false);
                    mButtonChoice3.setEnabled(false);
                }
            }
        });
    }
    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }
}