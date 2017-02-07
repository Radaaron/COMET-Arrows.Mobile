package com.example.aaron.arrowsmobile;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ChancePassengersFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private KeyHandler selectedTrip;
    private ArrayList<Integer> passengerList;

    public ChancePassengersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chance_passengers, container, false);
        selectedTrip = getArguments().getParcelable("selectedTrip");
        passengerList = new ArrayList<>();
        // check if passenger is part of passenger manifest
        for(int i = 0 ; i < selectedTrip.getPassengerIDList().size(); i++){
            if(selectedTrip.getBooleanFromDB(getContext(),
                    DBContract.Passenger.COLUMN_IS_CHANCE,
                    selectedTrip.getPassengerIDList().get(i),
                    DBContract.Passenger.TABLE_PASSENGER,
                    DBContract.Passenger.COLUMN_PASSENGER_ID)){
                passengerList.add(selectedTrip.getPassengerIDList().get(i));
            }
        }
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.chance_passengers_recycler_view);
        PassengerRecyclerAdapter adapter = new PassengerRecyclerAdapter(passengerList, getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(llm);
        return rootView;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
