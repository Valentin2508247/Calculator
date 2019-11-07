package com.example.calculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class BasicFragment extends Fragment implements View.OnClickListener{

    private Timer myTimer;
    private  MyTimerTask myTimerTask;
    private Button backspace_button;


    private OnBasicFragmentInteractionListener mListener;

    public BasicFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic, container, false);

        Button button = view.findViewById(R.id.equals_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.div_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.pow_button);
        button.setOnClickListener(this);
        ///////////////

        button = view.findViewById(R.id.back_button);
        backspace_button = button;
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        //start timer

                        Log.d("timer", "timer start");
                        if (myTimer != null)
                            myTimer.cancel();

                        myTimer = new Timer();
                        MyTimerTask task = new MyTimerTask();
                        myTimer.schedule(task, 500, 100);


                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        //disable timer
                        Log.d("timer", "timer end ");
                        myTimer.cancel();
                        onClick(backspace_button);
                        break;
                    }
                    default:
                        break;
                }



                return false;
            }
        });
        //button.setOnClickListener(this);

        //////////////
        button = view.findViewById(R.id.del_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.seven_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.eight_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.nine_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.mul_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.four_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.five_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.six_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.minus_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.one_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.two_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.three_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.plus_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.zero_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.dot_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.priority_button);
        button.setOnClickListener(this);


        return view;
    }


    public void onClick(final View view)
    {

        Button button = (Button)view;

        if (mListener != null) {
            mListener.onBasicFragmentInteraction(button.getText().toString());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BasicFragment.OnBasicFragmentInteractionListener) {
            mListener = (OnBasicFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onBasicFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnBasicFragmentInteractionListener {
        void onBasicFragmentInteraction(String str);
    }

    class MyTimerTask extends TimerTask
    {
        @Override
        public void run()
        {
            Log.d("timer", "timer method");

            Activity activity =  getActivity();

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    onClick(backspace_button);
                }
            });

        }

    }

}
