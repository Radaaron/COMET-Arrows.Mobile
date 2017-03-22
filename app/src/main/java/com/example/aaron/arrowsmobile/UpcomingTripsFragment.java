package com.example.aaron.arrowsmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class UpcomingTripsFragment extends Fragment implements OnTripSelectedListener{

    private OnFragmentInteractionListener mListener;
    private ArrayList<KeyHandler> tripList;

    public UpcomingTripsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_upcoming_trips, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.pending_trips_recycler_view);
        rv.setHasFixedSize(true);
        ArrayList<KeyHandler> fullList = getArguments().getParcelableArrayList("tripList");
        tripList = new ArrayList<>();
        String[] columns = {""}, selection = {""};
        String arrivalTime = "";

        // check if trip is pending
        for(int i = 0 ; i < fullList.size(); i++){
            arrivalTime = fullList.get(i).getStringFromDB(this.getContext(),
                    DBContract.Trip.COLUMN_ARRIVAL_TIME,
                    fullList.get(i).getTripID(),
                    DBContract.Trip.TABLE_TRIP,
                    DBContract.Trip.COLUMN_TRIP_ID);
            if(arrivalTime == null){
                tripList.add(fullList.get(i));
            }
        }

        TripRecyclerAdapter adapter = new TripRecyclerAdapter(tripList, this, getContext());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
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

    @Override
    public void onTripSelect(int tripIndex) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        TripDetailsDialogFragment dialogFragment = new TripDetailsDialogFragment();
        Bundle bundle = new Bundle();
        KeyHandler tripSelected = tripList.get(tripIndex);
        // get landing plate num and driver nickname
        String plateNum = tripSelected.getStringFromDB(getContext(),
                DBContract.Landing.COLUMN_LANDING_PLATE_NUM,
                1,
                DBContract.Landing.TABLE_LANDING,
                DBContract.Landing.COLUMN_LANDING_ID);
        String driver = tripSelected.getStringFromDB(getContext(),
                DBContract.Landing.COLUMN_LANDING_DRIVER,
                1,
                DBContract.Landing.TABLE_LANDING,
                DBContract.Landing.COLUMN_LANDING_ID);
        // driver and vehicle based on landing plate num and driver nickname
        tripSelected.setVehicleID(tripSelected.getStringFromDB(getContext(),
                DBContract.Vehicle.COLUMN_VEHICLE_ID,
                plateNum,
                DBContract.Vehicle.TABLE_VEHICLE,
                DBContract.Vehicle.COLUMN_PLATE_NUM
        ));
        tripSelected.setDriverID(Integer.parseInt(tripSelected.getStringFromDB(getContext(),
                DBContract.Driver.COLUMN_DRIVER_ID,
                driver,
                DBContract.Driver.TABLE_DRIVER,
                DBContract.Driver.COLUMN_NICKNAME
                )));
        bundle.putParcelable("tripSelected", tripSelected);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(ft, "dialog");
    }
}
