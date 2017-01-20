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


public class PendingTripsFragment extends Fragment implements OnTripSelectedListener{

    private OnFragmentInteractionListener mListener;
    private ArrayList<Trip> tripList;

    public PendingTripsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_pending_trips, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.pending_trips_recycler_view);
        rv.setHasFixedSize(true);
        ArrayList<Trip> fullList = getArguments().getParcelableArrayList("tripList");
        tripList = new ArrayList<>();
        // check if trip is pending
        for(int i = 0 ; i < fullList.size(); i++){
            if(fullList.get(i).getArrivalTime() == null){
                tripList.add(fullList.get(i));
            }
        }
        TripRecyclerAdapter adapter = new TripRecyclerAdapter(tripList, this);
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
        bundle.putParcelable("tripSelected", tripList.get(tripIndex));
        dialogFragment.setArguments(bundle);
        dialogFragment.show(ft, "dialog");
    }
}
