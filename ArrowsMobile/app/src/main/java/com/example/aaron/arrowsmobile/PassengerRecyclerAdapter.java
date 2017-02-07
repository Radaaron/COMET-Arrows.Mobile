package com.example.aaron.arrowsmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class PassengerRecyclerAdapter extends RecyclerView.Adapter<PassengerRecyclerAdapter.ViewHolder> {

    private ArrayList<Integer> mDataset;
    KeyHandler keyHandler;
    Context context;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox passengerCheckInBox;
        public CheckBox passengerCheckOutBox;
        public TextView passengerIdView;
        public ViewHolder(View v) {
            super(v);
            this.passengerCheckInBox = (CheckBox) v.findViewById(R.id.passenger_check_in_box);
            this.passengerCheckOutBox = (CheckBox) v.findViewById(R.id.passenger_check_out_box);
            this.passengerIdView = (TextView) v.findViewById(R.id.passenger_id_view);
        }
    }

        public PassengerRecyclerAdapter(ArrayList<Integer> myDataset, Context context) {
            this.mDataset = myDataset;
            this.keyHandler = new KeyHandler();
            this.context = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public PassengerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.passenger_list_item, parent, false);
            PassengerRecyclerAdapter.ViewHolder vh = new PassengerRecyclerAdapter.ViewHolder(v);
            return vh;
        }

        // Fills the contents of a view, tags each card, and implements onclick listener to return selected trip index
        @Override
        public void onBindViewHolder(final PassengerRecyclerAdapter.ViewHolder holder, int position) {
            holder.passengerIdView.setText(Integer.toString(mDataset.get(position)));
            // check if the passenger id has already been tapped in and out
            if(keyHandler.getStringFromDB(context,
                    DBContract.Passenger.COLUMN_TAP_IN,
                    Integer.toString(mDataset.get(position)),
                    DBContract.Passenger.TABLE_PASSENGER,
                    DBContract.Passenger.COLUMN_PASSENGER_ID) != null){
                holder.passengerCheckInBox.setChecked(true);
                holder.passengerCheckInBox.setEnabled(false);
                holder.passengerCheckOutBox.setEnabled(false);
                if(keyHandler.getStringFromDB(context,
                        DBContract.Passenger.COLUMN_TAP_OUT,
                        Integer.toString(mDataset.get(position)),
                        DBContract.Passenger.TABLE_PASSENGER,
                        DBContract.Passenger.COLUMN_PASSENGER_ID) != null){
                    holder.passengerCheckOutBox.setChecked(true);
                    holder.passengerCheckInBox.setEnabled(false);
                    holder.passengerCheckOutBox.setEnabled(false);
                }
            } else {
                holder.passengerCheckInBox.setEnabled(false);
                holder.passengerCheckOutBox.setEnabled(false);
            }
        }

        // Return the size of dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

}
