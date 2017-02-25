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


public class OnTripPassengersFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private KeyHandler selectedTrip;
    private ArrayList<Integer> passengerList, onTripPassengerList;

    public OnTripPassengersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trip_passengers, container, false);
        selectedTrip = getArguments().getParcelable("selectedTrip");
        passengerList = selectedTrip.getPassengerIDList();
        // only show the passengers that have been tapped in
        onTripPassengerList = new ArrayList<>();
        for(int i = 0; i < passengerList.size(); i++){
            if(selectedTrip.getStringFromDB(getContext(),
                    DBContract.Passenger.COLUMN_TAP_IN,
                    Integer.toString(passengerList.get(i)),
                    DBContract.Passenger.TABLE_PASSENGER,
                    DBContract.Passenger.COLUMN_PASSENGER_ID) != null){
                onTripPassengerList.add(passengerList.get(i));
            }
        }
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.trip_passengers_recycler_view);
        OnTripPassengerRecyclerAdapter adapter = new OnTripPassengerRecyclerAdapter(onTripPassengerList, getContext());
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
