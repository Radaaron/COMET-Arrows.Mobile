package com.example.aaron.arrowsmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class UpcomingTripsFragment extends Fragment implements OnTripSelectedListener{

    private OnFragmentInteractionListener mListener;
    private ArrayList<KeyHandler> filteredList;


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
        filteredList = getArguments().getParcelableArrayList("filteredList");
        TripRecyclerAdapter adapter = new TripRecyclerAdapter(filteredList, this, getContext());
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(), llm.getOrientation()); // item divider
        rv.addItemDecoration(dividerItemDecoration);
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
        KeyHandler tripSelected = filteredList.get(tripIndex);
        bundle.putParcelable("tripSelected", tripSelected);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(ft, "dialog");
    }
}
