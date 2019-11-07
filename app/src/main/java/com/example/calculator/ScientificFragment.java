package com.example.calculator;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ScientificFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "scientific_logs";



    private OnFragmentInteractionListener mListener;

    public ScientificFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scientific, container, false);

        Button button = view.findViewById(R.id.ln_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.factorial_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.sqrt_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.sin_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.cos_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.tan_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.abs_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.log_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.pi_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.e_button);
        button.setOnClickListener(this);

        Log.d(TAG, "onCreateView");
        return  view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        Log.d(TAG, "onAttach");
    }

    public void onClick(final View view)
    {
        Button button = (Button)view;

        if (mListener != null) {
            mListener.onFragmentInteraction(button.getText().toString());
            Log.d(TAG, "onClick, send text " + button.getText().toString());
        }
        else
            Log.d(TAG, "onClick, no listener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d(TAG, "onDetach");
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String str);
    }
}
