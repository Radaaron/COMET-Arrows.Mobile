package com.example.aaron.arrowsmobile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TripDetailsDialogFragment extends DialogFragment implements View.OnClickListener{

    OnFragmentInteractionListener mListener;
    KeyHandler selectedTrip;
    TextView tripDateView;
    TextView tripDepartureTimeView;
    TextView tripRouteView;
    TextView tripDriverView;
    TextView tripVehicleView;
    TextView tripPlateView;
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
        toolbar.setTitle("Trip Details");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);

        // trip details
        selectedTrip = getArguments().getParcelable("tripSelected");
        tripDateView = (TextView) rootView.findViewById(R.id.trip_details_date_view);
        tripDepartureTimeView = (TextView) rootView.findViewById(R.id.trip_details_time_view);
        tripRouteView = (TextView) rootView.findViewById(R.id.trip_details_route_view);
        tripDriverView = (TextView) rootView.findViewById(R.id.trip_details_driver_view);
        tripVehicleView = (TextView) rootView.findViewById(R.id.trip_details_vehicle_view);
        tripPlateView = (TextView) rootView.findViewById(R.id.trip_details_plate_view);
        embarkationStart = (Button) rootView.findViewById(R.id.start_embarkation_button);
        embarkationStart.setOnClickListener(this);

        tripDateView.setText(selectedTrip.getStringFromDB(this.getContext(),
                DBContract.Trip.COLUMN_TRIP_DATE,
                selectedTrip.getTripID(),
                DBContract.Trip.TABLE_TRIP,
                DBContract.Trip.COLUMN_TRIP_ID));

        tripDepartureTimeView.setText(selectedTrip.getStringFromDB(this.getContext(),
                DBContract.Trip.COLUMN_DEP_TIME,
                selectedTrip.getTripID(),
                DBContract.Trip.TABLE_TRIP,
                DBContract.Trip.COLUMN_TRIP_ID));

        tripRouteView.setText(selectedTrip.getStringFromDB(this.getContext(),
                DBContract.Route.COLUMN_ROUTE_NAME,
                selectedTrip.getRouteID(),
                DBContract.Route.TABLE_ROUTE,
                DBContract.Route.COLUMN_ROUTE_ID));

        tripDriverView.setText(selectedTrip.getStringFromDB(this.getContext(),
                DBContract.Driver.COLUMN_FIRST_NAME,
                selectedTrip.getDriverID(),
                DBContract.Driver.TABLE_DRIVER,
                DBContract.Driver.COLUMN_DRIVER_ID));

        tripDriverView.append(" " + selectedTrip.getStringFromDB(this.getContext(),
                DBContract.Driver.COLUMN_LAST_NAME,
                selectedTrip.getDriverID(),
                DBContract.Driver.TABLE_DRIVER,
                DBContract.Driver.COLUMN_DRIVER_ID));

        tripVehicleView.setText(selectedTrip.getStringFromDB(this.getContext(),
                DBContract.Vehicle.COLUMN_MODEL,
                selectedTrip.getVehicleID(),
                DBContract.Vehicle.TABLE_VEHICLE,
                DBContract.Vehicle.COLUMN_VEHICLE_ID));

        tripPlateView.setText(selectedTrip.getStringFromDB(this.getContext(),
                DBContract.Vehicle.COLUMN_PLATE_NUM,
                selectedTrip.getVehicleID(),
                DBContract.Vehicle.TABLE_VEHICLE,
                DBContract.Vehicle.COLUMN_VEHICLE_ID));

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
