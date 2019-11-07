package com.example.calculator;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolishNotation
    {
        public String[] operators = {"sin", "cos", "sqrt", "ln", "log", "tan", "abs"};
        private static final String TAG = "polish notaion";
        private EditText editText;
        private Context context;

        public PolishNotation(Context context , EditText editText)
        {
            this.context = context;
            this.editText = editText;
        }

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



            //replace (...)(...) with (...)*(...)
            for (int i = 1; i < s.length(); i++)
            {
                if ((Character.isDigit(s.charAt(i - 1)) && s.charAt(i) == '(') ||
                (s.charAt(i - 1) == ')' && Character.isDigit(s.charAt(i))) ||
                (s.charAt(i - 1) == ')' && s.charAt(i) == '('))
                {
                    s = s.substring(0, i) + '*' + s.substring(i);
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
                        Log.d(TAG, "execute " + a  + " + " + b);
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
                        Log.d(TAG, "execute sqrt(" + a + ")");
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
                editText.setText("0");
            }
            return res;
        }

        // TODO: finish method
        public Boolean isCorrect(String s)
        {
            //'()' - empty ()
            for (int i = 1; i < s.length(); i++)
            {
                if (s.charAt(i - 1) == '(' && s.charAt(i) == ')')
                {
                    Toast.makeText(context, "Empty '()'", Toast.LENGTH_LONG).show();
                    return false;
                }


            }


            //dealing with ')('
            int cnt = 0;
            for (int i = 0; i < s.length(); i++)
            {
                if (cnt < 0)
                {
                    Toast.makeText(context, "')' before '('", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (s.charAt(i) == ')')
                    cnt--;
                if (s.charAt(i) == '(')
                    cnt++;
            }
            if (cnt != 0)
            {
                Toast.makeText(context, "Different amount of '(' and ')'", Toast.LENGTH_LONG).show();
                return false;
            }


            //wrong amount of delimeters '.'
            for (int i = 0; i < s.length(); i++)
            {
                if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')
                {
                    if (s.charAt(i) == '.')
                        cnt++;
                }
                else
                {
                    cnt = 0;
                }
                if (cnt > 1) {
                    Toast.makeText(context, "Check '.' in your numbers", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            for (int i = 1; i < s.length(); i++)
            {
                if (isOperation(s.charAt(i - 1)) && isOperation(s.charAt(i)))
                {
                    Toast.makeText(context, "2 operations in a row", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            return true;
        }

        private Boolean isOperation(char ch)
        {
            if (ch == '^' || ch == '/' ||  ch == '*' ||  ch == '-' ||  ch == '+' ||  ch == '!')
                return true;
            return false;
        }
    }

