package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OnTripPassengerRecyclerAdapter extends RecyclerView.Adapter<EmbarkationPassengerRecyclerAdapter.ViewHolder>{

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

    public OnTripPassengerRecyclerAdapter(ArrayList<Integer> myDataset, Context context) {
        this.mDataset = myDataset;
        this.keyHandler = new KeyHandler();
        this.context = context;
        this.dbHandler = new DBHandler(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EmbarkationPassengerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passenger_list_item, parent, false);
        EmbarkationPassengerRecyclerAdapter.ViewHolder vh = new EmbarkationPassengerRecyclerAdapter.ViewHolder(v);
        return vh;
    }

    // Fills the contents of a view, tags each card, and implements onclick listener to return selected trip index
    @Override
    public void onBindViewHolder(final EmbarkationPassengerRecyclerAdapter.ViewHolder holder, int position) {
        // get user id of passenger
        final int userID = keyHandler.getIntFromDB(context,
                DBContract.Passenger.COLUMN_PASSENGER_USER,
                Integer.toString(mDataset.get(position)),
                DBContract.Passenger.TABLE_PASSENGER,
                DBContract.Passenger.COLUMN_PASSENGER_ID);
        // get user name from user id
        String passengerName = keyHandler.getStringFromDB(context,
                DBContract.User.COLUMN_NAME,
                Integer.toString(userID),
                DBContract.User.TABLE_USER,
                DBContract.User.COLUMN_ID_NUM);
        holder.passengerIdView.setText(Integer.toString(userID));
        holder.passengerNameView.setText(passengerName);
        holder.passengerCheckInBox.setChecked(true);
        holder.passengerCheckInBox.setEnabled(false);
        final int pos = position;
        // attach on long click for manual tap out
        holder.passengerCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                if(keyHandler.getStringFromDB(context,
                        DBContract.Passenger.COLUMN_TAP_OUT,
                        Integer.toString(mDataset.get(pos)),
                        DBContract.Passenger.TABLE_PASSENGER,
                        DBContract.Passenger.COLUMN_PASSENGER_ID) == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Manually Tap Out Passenger?");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SQLiteDatabase db = dbHandler.getWritableDatabase();
                            Calendar cal = Calendar.getInstance();
                            ContentValues cv = new ContentValues();
                            cv.put(DBContract.Passenger.COLUMN_TAP_OUT, timeFormat.format(cal.getTime()));
                            db.update(DBContract.Passenger.TABLE_PASSENGER, cv, DBContract.Passenger.COLUMN_PASSENGER_ID + "=" + Integer.toString(mDataset.get(pos)), null);
                            Toast.makeText(context, "Passenger Tapped Out", Toast.LENGTH_LONG).show();
                            mDataset.remove(pos);
                            notifyItemRemoved(pos);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
                return false;
            }
        });
    }



    // Return the size of dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
