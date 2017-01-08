package com.example.aaron.arrowsmobile;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TripRecyclerAdapter extends RecyclerView.Adapter<TripRecyclerAdapter.ViewHolder> {

    private OnTripSelectedListener tripSelectedListener;
    private int selected = -1;
    private ArrayList<Trip> mDataset;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView tripCardView;
        public TextView tripTimeView;
        public TextView tripRouteView;
        public TextView tripCapacityView;
        public ViewHolder(View v) {
            super(v);
            this.tripCardView = (CardView) v.findViewById(R.id.trip_card_view);
            this.tripTimeView = (TextView) v.findViewById(R.id.trip_time_view);
            this.tripRouteView = (TextView) v.findViewById(R.id.trip_route_view);
            this.tripCapacityView = (TextView) v.findViewById(R.id.trip_capacity_view);
        }
    }

    public TripRecyclerAdapter(ArrayList<Trip> myDataset, OnTripSelectedListener listener) {
        this.mDataset = myDataset;
        this.tripSelectedListener = listener;
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
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        holder.tripTimeView.setText(timeFormat.format(mDataset.get(position).getDepTime().getTime()));
        holder.tripRouteView.setText(mDataset.get(position).getTripSched().getRoute().getRouteName());
        // "passenger count / capacity"
        holder.tripCapacityView.setText(mDataset.get(position).getPassengerCount() + "/" + mDataset.get(position).getVehicle().getCapacity());
        holder.tripCardView.setTag(position);
        holder.tripCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = (int) v.getTag();
                tripSelectedListener.onTripSelect(selected);
            }
        });
    }

    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
