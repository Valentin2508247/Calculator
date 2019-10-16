package com.example.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class BasicFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnBasicFragmentInteractionListener mListener;

    public BasicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicFragment newInstance(String param1, String param2) {
        BasicFragment fragment = new BasicFragment();
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
        View view = inflater.inflate(R.layout.fragment_basic, container, false);

        Button button = view.findViewById(R.id.equals_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.div_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.pow_button);
        button.setOnClickListener(this);
        button = view.findViewById(R.id.back_button);
        button.setOnClickListener(this);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onClick(final View view)
    {

        FragmentManager fragmentManager = getFragmentManager();
        BasicFragment fragment = (BasicFragment) fragmentManager.findFragmentById(R.id.basicFragment);
        Button button = fragment.getView().findViewById(view.getId());

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
        // TODO: Update argument type and name
        void onBasicFragmentInteraction(String str);
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

}
