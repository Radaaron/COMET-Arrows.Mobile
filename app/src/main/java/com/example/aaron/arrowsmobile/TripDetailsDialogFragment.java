package com.example.aaron.arrowsmobile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripDetailsDialogFragment extends DialogFragment implements View.OnClickListener{

    OnFragmentInteractionListener mListener;
    KeyHandler selectedTrip;

    Button embarkationStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.trip_details_dialog, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.dialog_toolbar);
        selectedTrip = getArguments().getParcelable("tripSelected");
        SimpleDateFormat sentFormat = new SimpleDateFormat("h:mm:ss a");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        Date date = null;
        try {
            date = sentFormat.parse(selectedTrip.getStringFromDB(getContext(),
                    DBContract.TripSched.COLUMN_DEP_TIME,
                    selectedTrip.getTripSchedID(),
                    DBContract.TripSched.TABLE_TRIP_SCHED,
                    DBContract.TripSched.COLUMN_TRIP_SCHED_ID));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String depTime = timeFormat.format(date);
        String route = selectedTrip.getStringFromDB(getContext(), DBContract.Route.COLUMN_ROUTE_ORIGIN, selectedTrip.getRouteID(), DBContract.Route.TABLE_ROUTE, DBContract.Route.COLUMN_ROUTE_ID)
                + " to " + selectedTrip.getStringFromDB(getContext(), DBContract.Route.COLUMN_ROUTE_DESTINATION, selectedTrip.getRouteID(), DBContract.Route.TABLE_ROUTE, DBContract.Route.COLUMN_ROUTE_ID);
        toolbar.setTitle(depTime + " | " + route);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        setHasOptionsMenu(true);

        // trip passenger manifest
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.landing_manifest_recycler_view);
        PassengerManifestRecyclerAdapter adapter = new PassengerManifestRecyclerAdapter(selectedTrip.getPassengerIDList(), getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation()); // item divider
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(llm);
        embarkationStart = (Button) rootView.findViewById(R.id.start_embarkation_button);
        embarkationStart.setOnClickListener(this);

        return rootView;
    }

    // for fullscreen dialog
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            dismiss();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentDialogInteractionListener");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.start_embarkation_button) {
            mListener.onFragmentInteraction(selectedTrip);
            dismiss();
        }
    }

}
