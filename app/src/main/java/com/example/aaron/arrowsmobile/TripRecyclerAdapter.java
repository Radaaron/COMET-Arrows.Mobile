package com.example.aaron.arrowsmobile;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TripRecyclerAdapter extends RecyclerView.Adapter<TripRecyclerAdapter.ViewHolder> {

    private OnTripSelectedListener tripSelectedListener;
    private int selected = -1;
    private ArrayList<KeyHandler> mDataset;
    Context context;
    String depTime = "", routeName = "";
    int passengerCount = 0;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView tripCardView;
        public TextView tripTimeView;
        public TextView tripRouteView;
        public TextView tripPassengerCountView;
        public ViewHolder(View v) {
            super(v);
            this.tripCardView = (CardView) v.findViewById(R.id.trip_card_view);
            this.tripTimeView = (TextView) v.findViewById(R.id.trip_time_view);
            this.tripRouteView = (TextView) v.findViewById(R.id.trip_route_view);
            this.tripPassengerCountView = (TextView) v.findViewById(R.id.trip_passenger_count_view);
        }
    }

    public TripRecyclerAdapter(ArrayList<KeyHandler> myDataset, OnTripSelectedListener listener, Context context) {
        this.mDataset = myDataset;
        this.tripSelectedListener = listener;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TripRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Fills the contents of a view, tags each card, and implements onclick listener to return selected trip index
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        populateCardItem(position);
        holder.tripTimeView.setText(depTime);
        holder.tripRouteView.setText(routeName);
        // "passenger count / capacity"
        holder.tripPassengerCountView.setText("" + passengerCount);
        holder.tripCardView.setTag(position);
        holder.tripCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = (int) v.getTag();
                tripSelectedListener.onTripSelect(selected);
            }
        });
    }

    public void populateCardItem(int position){
        depTime = mDataset.get(position).getStringFromDB(context,
                DBContract.Trip.COLUMN_DEP_TIME,
                mDataset.get(position).getTripID(),
                DBContract.Trip.TABLE_TRIP,
                DBContract.Trip.COLUMN_TRIP_ID);

        routeName = mDataset.get(position).getStringFromDB(context,
                DBContract.Route.COLUMN_ROUTE_DESTINATION,
                mDataset.get(position).getRouteID(),
                DBContract.Route.TABLE_ROUTE,
                DBContract.Route.COLUMN_ROUTE_ID);

        passengerCount = mDataset.get(position).getPassengerIDList().size();
    }

    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
