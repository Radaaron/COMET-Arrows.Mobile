package com.example.aaron.arrowsmobile;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class PassengerManifestRecyclerAdapter extends RecyclerView.Adapter<PassengerManifestRecyclerAdapter.ViewHolder>{

    private ArrayList<Integer> mDataset;
    KeyHandler keyHandler;
    DBHandler dbHandler;
    Context context;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView passengerCardView;
        public CheckBox passengerCheckInBox;
        public TextView passengerIdView;
        public TextView passengerNameView;
        public ViewHolder(View v) {
            super(v);
            this.passengerCardView = (CardView) v.findViewById(R.id.passenger_card_view);
            this.passengerCheckInBox = (CheckBox) v.findViewById(R.id.passenger_check_in_box);
            this.passengerIdView = (TextView) v.findViewById(R.id.passenger_id_view);
            this.passengerNameView = (TextView) v.findViewById(R.id.passenger_name_view);
        }
    }

    public PassengerManifestRecyclerAdapter(ArrayList<Integer> myDataset, Context context) {
        this.mDataset = myDataset;
        this.keyHandler = new KeyHandler();
        this.context = context;
        this.dbHandler = new DBHandler(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PassengerManifestRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passenger_list_item, parent, false);
        PassengerManifestRecyclerAdapter.ViewHolder vh = new PassengerManifestRecyclerAdapter.ViewHolder(v);
        return vh;
    }

    // Fills the contents of a view, tags each card, and implements onclick listener to return selected trip index
    @Override
    public void onBindViewHolder(final PassengerManifestRecyclerAdapter.ViewHolder holder, int position) {
        // get reservationNum of passenger
        int reservationNum = keyHandler.getIntFromDB(context,
                DBContract.Passenger.COLUMN_RESERVATION_NUM,
                Integer.toString(mDataset.get(position)),
                DBContract.Passenger.TABLE_PASSENGER,
                DBContract.Passenger.COLUMN_PASSENGER_ID);
        // get userID from reservationNum
        final int userID = keyHandler.getIntFromDB(context,
                DBContract.Reservation.COLUMN_ID_NUM,
                Integer.toString(reservationNum),
                DBContract.Reservation.TABLE_RESERVATION,
                DBContract.Reservation.COLUMN_RESERVATION_NUM);
        // get user name from userID
        String passengerName = keyHandler.getStringFromDB(context,
                DBContract.User.COLUMN_NAME,
                Integer.toString(userID),
                DBContract.User.TABLE_USER,
                DBContract.User.COLUMN_ID_NUM);
        holder.passengerIdView.setText(Integer.toString(userID));
        holder.passengerNameView.setText(passengerName);
        // for viewing only
        holder.passengerCheckInBox.setVisibility(View.INVISIBLE);
    }



    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
