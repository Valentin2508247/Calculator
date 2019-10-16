package com.example.calculator;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ScientificFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ScientificFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Scientificragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScientificFragment newInstance(String param1, String param2) {
        ScientificFragment fragment = new ScientificFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
    }

    public void onClick(final View view)
    {
        FragmentManager fragmentManager = getFragmentManager();
        ScientificFragment fragment = (ScientificFragment) fragmentManager.findFragmentById(R.id.scientificFragment);
        Button button = fragment.getView().findViewById(view.getId());

        if (mListener != null) {
            mListener.onFragmentInteraction(button.getText().toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String str);
    }
}
