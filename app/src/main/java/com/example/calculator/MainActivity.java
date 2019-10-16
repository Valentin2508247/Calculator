package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements ScientificFragment.OnFragmentInteractionListener, BasicFragment.OnBasicFragmentInteractionListener{
    private PolishNotation polishNotation;
    boolean isLandscapeRequested;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();
        EditText editText = findViewById(R.id.edit_text);
        //disable keyboard
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            editText.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            editText.setTextIsSelectable(true);
        }

        polishNotation = new PolishNotation();
    }



        /*public void onClick(View view)
        {
            EditText edit = findViewById(R.id.edit_text);
            edit.setText(Double.toString(polishNotation.Execute(edit.getText().toString())));
            Toast.makeText(this, Integer.toString(edit.getSelectionStart()), Toast.LENGTH_LONG);
        }*/


        /*@Override
        public void onConfigurationChanged(Configuration newConfig)
        {
            if (!isLandscapeRequested)
            {
                setRequestedOrientation(newConfig.orientation);
            }
        }*/


    public void switchMode(View view)
    {
        if (!isLandscapeRequested)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isLandscapeRequested = true;
        }
        else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            isLandscapeRequested = false;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("isLandscapeRequested", isLandscapeRequested);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        isLandscapeRequested = savedInstanceState.getBoolean("isLandscapeRequested");
    }


    @Override
    public void onFragmentInteraction(String str)
    {

        ScientificFragment fragment = (ScientificFragment) getSupportFragmentManager().findFragmentById(R.id.scientificFragment);
        if (fragment != null && fragment.isInLayout())
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
                    edit.setText(Double.toString(polishNotation.Execute(edit.getText().toString())));
                    edit.setSelection(edit.getText().length());
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




    class PolishNotation
    {
        public String[] operators = {"sin", "cos", "sqrt", "ln", "log", "tan", "abs"};

        private int getPriority(String operand) {
            switch (operand) {
                case "+":
                case "-": {
                    return 2;
                }
                case "*":
                case "/": {
                    return 3;
                }
                case "^":
                case "sin":
                case "cos":
                case "tan":
                case "ln":
                case "sqrt":
                case "log":
                case "abs":
                case "m":{
                    return 4;
                }
                case "(":
                case ")":{
                    return 1;
                }
                case "!": {
                    return 0;
                }
                default: {
                    try {
                        throw new Exception("Unimplemented operation");
                    } catch (Exception e) {
                    }

                    return 0;
                }
            }

        }

        private String replace(String s)
        {   //replace "-x" with "mx"
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '-') {
                    if (i == 0 || s.charAt(i - 1) == '(')
                        s = s.substring(0, i) + 'm' + s.substring(i + 1);
                }
            }
            return s;
        }


        public Queue<String> parseToQueue(String str) {
            Queue<String> queue = new LinkedList<>();
            Pattern double_pattern = Pattern.compile("^\\d+(\\.\\d+)?");
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/' || str.charAt(i) == '!' || str.charAt(i) == '(' || str.charAt(i) == ')' || str.charAt(i) == '^' || str.charAt(i) == 'm') {
                    queue.add("" + str.charAt(i));
                }
                else if (str.charAt(i) == 'e')
                    queue.add(Double.toString(Math.E));
                else if (str.charAt(i) == '\u03c0')
                    queue.add(Double.toString(Math.PI));

                else if (Character.isDigit(str.charAt(i))) {
                    String s = str.substring(i);
                    Matcher matcher = double_pattern.matcher(s);
                    if (matcher.find()) {
                        queue.add(matcher.group());
                        i = i + matcher.group().length() - 1;
                    }
                }
                else {
                    String s = str.substring(i);
                    for (String op : operators) {
                        Pattern pattern = Pattern.compile("^" + op);
                        Matcher matcher = pattern.matcher((s));
                        if (matcher.find()) {
                            queue.add(matcher.group());
                            i = i + matcher.group().length() - 1;
                            break;
                        }
                    }
                }
            }

            return queue;
        }

        public Queue<String> toReverseNotation(Queue<String> inf) {
            Pattern num_pattern = Pattern.compile("^\\d+(\\.\\d+)?");
            Queue<String> reverse = new LinkedList<>();
            Stack<String> operators = new Stack<>();
            String next = inf.remove();
            while (inf.size() >= 0) {
         //       System.out.println(("Next = '" + next + "'"));
                if (num_pattern.matcher(next).matches())
                    reverse.add(next);
                else if (next.equals("!"))
                    reverse.add(next);
                else if (next.equals("sin") || next.equals("cos") || next.equals("sqrt") || next.equals("tan") || next.equals("ln") || next.equals("log") || next.equals("abs") || next.equals("m"))
                    operators.push(next);
                else if (next.equals("("))
                {
           //         System.out.println("Pushing '" + next + "'");
                    operators.push(next);
                }
                else if (next.equals(")")) {
                    String top = operators.pop();
                    while (!top.equals("(")) {
                        reverse.add(top);
                        top = operators.pop();
                    }
                }
                else if (next.equals(")")) {
                    String top = operators.pop();
                    while (!top.equals("(")) {
                        reverse.add(top);
                        top = operators.pop();
                    }
                }
                else {
                    while (operators.size() > 0 && getPriority(operators.peek()) >= getPriority(next))
                        reverse.add(operators.pop());
             //       System.out.println("Pushing operation '" + next +"'");
                    operators.push(next);

                }
               /*
                for (String el : operators)
                    System.out.print(el);
                System.out.print("  out: " );
                for (String el : reverse)
                    System.out.print(el);
                System.out.println();
                */
                if (inf.size() > 0) {
                    next = inf.remove();
                } else {
                    while (operators.size() > 0)
                        reverse.add(operators.pop());
                    break;
                }
            }


            return reverse;
        }

        public double getResult(Queue<String> queue)
        {
            Stack<Double> stack = new Stack<>();
            String next = queue.remove();
            double temp = 0;
            while(queue.size() >= 0)
            {
                switch (next)
                {
                    case "+":
                    {
                        double a = stack.pop(), b = stack.pop();
                        temp = a + b;
                        stack.push(temp);
                        break;
                    }
                    case "-":
                    {
                        double a = stack.pop(), b = stack.pop();
                        temp = b - a;
                        stack.push(temp);
                        break;
                    }
                    case "*":
                    {
                        double a = stack.pop(), b = stack.pop();
                        temp = a * b;
                        stack.push(temp);
                        break;
                    }
                    case "^":
                    {
                        double a = stack.pop(), b = stack.pop();
                        temp = Math.pow(b, a);
                        stack.push(temp);
                        break;
                    }
                    case "/":
                    {
                        double a = stack.pop(), b = stack.pop();
                        temp = b / a;
                        stack.push(temp);
                        break;
                    }
                    case "!":
                    {
                        long a = (long)(stack.pop()).doubleValue();
                        if (a >= 16)
                            a++;//exception
                        else
                        {
                            long t = 1;
                            for (int i = 1; i <= a; i++)
                                t *= i;
                            stack.push((double)t);
                        }
                        break;
                    }
                    case "sin":
                    {
                        double a = stack.pop();
                        temp = Math.sin(a);
                        stack.push(temp);
                        break;
                    }
                    case "cos":
                    {
                        double a = stack.pop();
                        temp = Math.cos(a);
                        stack.push(temp);
                        break;
                    }
                    case "tan": {
                        double a = stack.pop();
                        temp = Math.tan(a);
                        stack.push(temp);
                        break;
                    }
                    case "sqrt":
                    {
                        double a = stack.pop();
                        temp = Math.sqrt(a);
                        stack.push(temp);
                        break;
                    }
                    case "ln":
                    {
                        double a = stack.pop();
                        temp = Math.log(a);
                        stack.push(temp);
                        break;
                    }
                    case "log":
                    {
                        double a  = stack.pop();
                        temp = Math.log10(a);
                        stack.push(temp);
                        break;
                    }
                    case "abs":
                    {
                        double a  = stack.pop();
                        temp = Math.abs(a);
                        stack.push(temp);
                        break;
                    }
                    case "m":
                    {
                        stack.push( -stack.pop());
                        break;
                    }
                    default:
                    {

                        stack.push(Double.parseDouble(next));
                        break;
                    }
                }
                if (queue.size() == 0)
                    return stack.pop();

                else
                    next = queue.remove();
            }
            try {


                if (stack.size() > 0)
                    throw new Exception("Operators and operands do not match");
            }
            catch (Exception ex)
            {

            }
            return temp;
        }

        public double Execute(String str)
        {
            double res = 0;
            try {
                res = getResult(toReverseNotation(parseToQueue(replace(str))));
            }
            catch (Exception ex)
            {
                EditText edit = findViewById(R.id.edit_text);
                edit.setText("0");
            }
            return res;
        }
    }

}
