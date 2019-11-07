package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements ScientificFragment.OnFragmentInteractionListener, BasicFragment.OnBasicFragmentInteractionListener, View.OnClickListener {
    private PolishNotation polishNotation;
    private static final String TAG = "main_logs";
    boolean isScientificRequested;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");

        if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();

        //disable keyboard
        EditText editText = findViewById(R.id.edit_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            editText.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            editText.setTextIsSelectable(true);
        }

        polishNotation = new PolishNotation(this, (EditText) findViewById(R.id.edit_text));
        isScientificRequested = false;

        //TODO: implement long press on backspace button (OnTouchListener())
        //TODO: перестать заменять содержимое строки на 0 и показывать пользователю его ошибки



    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }




    public void onClick(View view)
    {
        EditText edit = findViewById(R.id.edit_text);
        edit.setText(((Button)view).getText());
    }


    public void switchMode(View view)
    {

        FrameLayout frameLayout = findViewById(R.id.frame);

        if (isScientificRequested)
        {
            isScientificRequested = false;

            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0));
            Log.d(TAG, "Unrequeast scientific mode");
        }
        else
        {
            isScientificRequested = true;

            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2));

            Log.d(TAG, "Request scientific mode");
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            ScientificFragment fragment = new ScientificFragment();
            transaction.add(R.id.frame, fragment, "mytag");
            transaction.commit();
        }



    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        Log.d(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean("isLandscapeRequested", isScientificRequested);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState");
        isScientificRequested = savedInstanceState.getBoolean("isLandscapeRequested");
    }


    @Override
    public void onFragmentInteraction(String str)
    {
        Log.d(TAG, "onFragmentInteraction");
        {
            EditText edit = findViewById(R.id.edit_text);
            switch (str)
            {
                case "!":
                case "e":
                case "\u03c0":
                {
                    int pos = edit.getSelectionStart();
                    String s = edit.getText().toString();
                    s = s.substring(0, pos) + str + s.substring(pos);
                    edit.setText(s);
                    edit.setSelection(pos + str.length());
                    break;
                }
                case "|x|":
                {
                    int pos = edit.getSelectionStart();
                    String s = edit.getText().toString();
                    s = s.substring(0, pos) + "abs()" + s.substring(pos);
                    edit.setText(s);
                    edit.setSelection(pos + 4);
                    break;
                }
                case "\u221a":
                {
                    int pos = edit.getSelectionStart();
                    String s = edit.getText().toString();
                    s = s.substring(0, pos) + "sqrt()" + s.substring(pos);
                    edit.setText(s);
                    edit.setSelection(pos + 5);
                    break;
                }
                default:
                {
                    int pos = edit.getSelectionStart();
                    String s = edit.getText().toString();
                    s = s.substring(0, pos) + str + "()" + s.substring(pos);
                    edit.setText(s);
                    edit.setSelection(pos + str.length() + 1);
                }

            }


        }
    }

    @Override
    public void onBasicFragmentInteraction(String str)
    {
        BasicFragment fragment = (BasicFragment)getSupportFragmentManager().findFragmentById(R.id.basicFragment);
        if (fragment != null && fragment.isInLayout())
        {
            EditText edit = findViewById(R.id.edit_text);

            switch (str)
            {
                case "=":
                {
                    String s = edit.getText().toString();
                    if (polishNotation.isCorrect(s))
                    {
                        edit.setText(Double.toString(polishNotation.Execute(s)));
                        edit.setSelection(edit.getText().length());
                    }

                    break;
                }
                case "\u232b":
                {
                    int pos = edit.getSelectionStart();
                    String s = edit.getText().toString();
                    if (pos >= 1)
                    {
                        s = s.substring(0, pos - 1) + s.substring(pos);
                        edit.setText(s);
                        edit.setSelection(pos - 1);
                    }

                    break;
                }
                case "del":
                {
                    edit.setText("");
                    break;
                }
                case "()":
                {
                    int pos = edit.getSelectionStart();
                    String s = edit.getText().toString();
                    s = s.substring(0, pos) + str + s.substring(pos);
                    edit.setText(s);
                    edit.setSelection(pos + 1);
                    break;
                }
                default:
                {
                    int pos = edit.getSelectionStart();
                    String s = edit.getText().toString();
                    s = s.substring(0, pos) + str + s.substring(pos);
                    edit.setText(s);
                    edit.setSelection(pos + str.length());
                }

            }


        }
    }
}
