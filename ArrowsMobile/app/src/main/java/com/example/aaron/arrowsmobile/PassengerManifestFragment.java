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


public class PassengerManifestFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Trip selectedTrip;
    private ArrayList<Passenger> passengerList;

    public PassengerManifestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_passenger_manifest, container, false);
        selectedTrip = getArguments().getParcelable("selectedTrip");
        ArrayList<Passenger> fullList = selectedTrip.getPassengerList();
        passengerList = new ArrayList<>();
        // check if passenger is part of passenger manifest
        for(int i = 0 ; i < fullList.size(); i++){
            if(!fullList.get(i).isChance()){
                passengerList.add(fullList.get(i));
            }
        }
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.passenger_manifest_recycler_view);
        PassengerRecyclerAdapter adapter = new PassengerRecyclerAdapter(passengerList);
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
