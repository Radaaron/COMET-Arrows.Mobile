package com.example.aaron.arrowsmobile;

import android.provider.BaseColumns;

import static com.example.aaron.arrowsmobile.DBContract.Landing.TABLE_LANDING;
import static com.example.aaron.arrowsmobile.DBContract.Reservation.TABLE_RESERVATION;
import static com.example.aaron.arrowsmobile.DBContract.Stop.COLUMN_STOP_ID;
import static com.example.aaron.arrowsmobile.DBContract.Trip.TABLE_TRIP;
import static com.example.aaron.arrowsmobile.DBContract.TripSched.TABLE_TRIP_SCHED;
import static com.example.aaron.arrowsmobile.DBContract.User.TABLE_USER;
import static com.example.aaron.arrowsmobile.DBContract.Vehicle.TABLE_VEHICLE;

public final class DBContract {
    // Contract class for database
    // private constructor to avoid instantiation
    private DBContract(){}

    public static final String DB_NAME = "arrowsDB.db";

    // db-entity classes (alphabetical, only important tables)

    public static class Driver implements BaseColumns{
        public static final String TABLE_DRIVER = "driver";
        public static final String COLUMN_DRIVER_ID = "driver_id";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_NICKNAME = "nickname";
    }

    public static class Line implements BaseColumns{
        public static final String TABLE_LINE = "line";
        public static final String COLUMN_LINE_NUM = "line_num";
        public static final String COLUMN_LINE_NAME = "line_name";
    }

    public static class Passenger implements BaseColumns{
        public static final String TABLE_PASSENGER = "passenger";
        public static final String COLUMN_PASSENGER_ID = "passenger_id";
        public static final String COLUMN_FEEDBACK_ON = "feedback_on";
        public static final String COLUMN_FEEDBACK = "feedback";
        public static final String COLUMN_TAP_IN = "tap_in";
        public static final String COLUMN_TAP_OUT = "tap_out";
        public static final String COLUMN_DISEMBARKATION_PT = "disembarkation_pt";
        public static final String COLUMN_DESTINATION = "destination";
        public static final String COLUMN_PASSENGER_RESERVATION = "passenger_reservation_number";
        public static final String COLUMN_PASSENGER_VEHICLE = "passenger_vehicle";
        public static final String COLUMN_PASSENGER_DRIVER = "passenger_driver";
    }

    public static class Reservation implements BaseColumns{
        public static final String TABLE_RESERVATION = "reservation";
        public static final String COLUMN_RESERVATION_NUM = "reservation_num";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_DESTINATION = "destination";
        public static final String COLUMN_REMARK = "remark";
        public static final String COLUMN_RESERVATION_TRIP = "reservation_trip";
        public static final String COLUMN_RESERVATION_ROUTE_STOP = "reservation_route_stop";
        public static final String COLUMN_RESERVATION_USER = "reservation_user";
    }

    public static class Route implements BaseColumns{
        public static final String TABLE_ROUTE = "route";
        public static final String COLUMN_ROUTE_ID = "route_id";
        public static final String COLUMN_ROUTE_ORIGIN = "route_origin";
        public static final String COLUMN_ROUTE_NAME = "route_name";
        public static final String COLUMN_ROUTE_DESTINATION = "route_destination";
        public static final String COLUMN_ROUTE_DESCRIPTION = "route_description";
        public static final String COLUMN_ROUTE_LINE = "route_line";
    }

    public static class RouteStop implements BaseColumns{
        public static final String TABLE_ROUTE_STOP = "route_stop";
        public static final String COLUMN_STOP_NUM = "stop_num";
        public static final String COLUMN_ORDER = "stop_order";
        public static final String COLUMN_ROUTE_STOP_STOP = "route_stop_stop";
        public static final String COLUMN_ROUTE_STOP_ROUTE = "route_stop_route";
    }

    public static class Stop implements BaseColumns{
        public static final String TABLE_STOP = "stop";
        public static final String COLUMN_STOP_ID = "stop_id";
        public static final String COLUMN_STOP_NAME = "stop_name";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }

    public static class Trip implements BaseColumns{
        public static final String TABLE_TRIP = "trip";
        public static final String COLUMN_TRIP_ID = "trip_id";
        public static final String COLUMN_REMARKS = "remarks";
        public static final String COLUMN_TRIP_DATE = "trip_date";
        public static final String COLUMN_DEP_TIME = "dep_time";
        public static final String COLUMN_ARRIVAL_TIME = "arrival_time";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_IS_SPECIAL = "is_special";
        public static final String COLUMN_SP_NUM_PASS = "sp_num_pass";
        public static final String COLUMN_PURPOSE = "purpose";
        public static final String COLUMN_TRIP_VEHICLE = "trip_vehicle";
        public static final String COLUMN_TRIP_DRIVER = "trip_driver";
        public static final String COLUMN_TRIP_TRIP_SCHED = "trip_trip_sched";
    }

    public static class TripSched implements BaseColumns{
        public static final String TABLE_TRIP_SCHED = "trip_sched";
        public static final String COLUMN_TRIP_SCHED_ID = "trip_sched_id";
        public static final String COLUMN_TRIP_NUM = "trip_num";
        public static final String COLUMN_DEP_TIME = "dep_time";
        public static final String COLUMN_TRIP_SCHED_ROUTE = "trip_sched_route";
    }

    public static class User implements BaseColumns{
        public static final String TABLE_USER = "user";
        public static final String COLUMN_ID_NUM = "id_num";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_EMERGENCY_CONTACT = "emergency_contact";
        public static final String COLUMN_EMERGENCY_CONTACT_NUM = "emergency_contact_num";
        public static final String COLUMN_IS_ADMIN = "is_admin";
        public static final String COLUMN_ADMIN_PASSWORD = "admin_password";
        public static final String COLUMN_AP_PRIORITY_ID = "ap_priority_id";
    }

    public static class Vehicle implements BaseColumns{
        public static final String TABLE_VEHICLE = "vehicle";
        public static final String COLUMN_VEHICLE_ID = "vehicle_id";
        public static final String COLUMN_VEHICLE_TYPE = "vehicle_type";
        public static final String COLUMN_CAPACITY = "capacity";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_PLATE_NUM = "plate_num";
        public static final String COLUMN_MODEL = "model";
        public static final String COLUMN_BRAND = "brand";
    }

    // app-only tables
    public static class Landing implements BaseColumns{
        public static final String TABLE_LANDING = "landing";
        public static final String COLUMN_LANDING_ID = "landing_id";
        public static final String COLUMN_LANDING_PLATE_NUM = "landing_plate_num";
        public static final String COLUMN_LANDING_DRIVER = "landing_plate_driver";
    }

    // create

    public static final String CREATE_TABLE_DRIVER = "CREATE TABLE "
            + Driver.TABLE_DRIVER + " ("
            + Driver.COLUMN_DRIVER_ID + " INTEGER PRIMARY KEY, "
            + Driver.COLUMN_LAST_NAME + " VARCHAR, "
            + Driver.COLUMN_FIRST_NAME + " VARCHAR, "
            + Driver.COLUMN_NICKNAME + " VARCHAR )";

    public static final String CREATE_TABLE_LINE = "CREATE TABLE "
            + Line.TABLE_LINE + " ("
            + Line.COLUMN_LINE_NUM + " INTEGER PRIMARY KEY, "
            + Line.COLUMN_LINE_NAME + " VARCHAR )";

    public static final String CREATE_TABLE_PASSENGER = "CREATE TABLE "
            + Passenger.TABLE_PASSENGER + " ("
            + Passenger.COLUMN_PASSENGER_ID + " INTEGER PRIMARY KEY, "
            + Passenger.COLUMN_FEEDBACK_ON + " VARCHAR, "
            + Passenger.COLUMN_FEEDBACK + " INTEGER, "
            + Passenger.COLUMN_TAP_IN + " DATETIME, "
            + Passenger.COLUMN_TAP_OUT + " DATETIME, "
            + Passenger.COLUMN_DISEMBARKATION_PT + " VARCHAR, "
            + Passenger.COLUMN_DESTINATION + " VARCHAR, "
            + Passenger.COLUMN_PASSENGER_RESERVATION + " INTEGER, "
            + Passenger.COLUMN_PASSENGER_VEHICLE + " VARCHAR, "
            + Passenger.COLUMN_PASSENGER_DRIVER + " INTEGER, "
            + " FOREIGN KEY ( " + Passenger.COLUMN_PASSENGER_RESERVATION + " ) REFERENCES " + Reservation.TABLE_RESERVATION + "( " + Reservation.COLUMN_RESERVATION_NUM + " ),"
            + " FOREIGN KEY ( " + Passenger.COLUMN_PASSENGER_VEHICLE + " ) REFERENCES " + Vehicle.TABLE_VEHICLE + "( " + Vehicle.COLUMN_VEHICLE_ID + " ),"
            + " FOREIGN KEY ( " + Passenger.COLUMN_PASSENGER_DRIVER + " ) REFERENCES " + Driver.TABLE_DRIVER + "( " + Driver.COLUMN_DRIVER_ID + " ))";

    public static final String CREATE_TABLE_RESERVATION = "CREATE TABLE "
            + TABLE_RESERVATION + " ("
            + Reservation.COLUMN_RESERVATION_NUM + " VARCHAR PRIMARY KEY, "
            + Reservation.COLUMN_TIMESTAMP + " DATETIME, "
            + Reservation.COLUMN_DESTINATION + " VARCHAR, "
            + Reservation.COLUMN_REMARK + " VARCHAR,"
            + Reservation.COLUMN_RESERVATION_TRIP + " INTEGER, "
            + Reservation.COLUMN_RESERVATION_ROUTE_STOP + " VARCHAR, "
            + Reservation.COLUMN_RESERVATION_USER + " INTEGER,"
            + " FOREIGN KEY ( " + Reservation.COLUMN_RESERVATION_TRIP + " ) REFERENCES " + Trip.TABLE_TRIP + "( " + Trip.COLUMN_TRIP_ID + " ),"
            + " FOREIGN KEY ( " + Reservation.COLUMN_RESERVATION_ROUTE_STOP + " ) REFERENCES " + RouteStop.TABLE_ROUTE_STOP + "( " + RouteStop.COLUMN_STOP_NUM + " ),"
            + " FOREIGN KEY ( " + Reservation.COLUMN_RESERVATION_USER + " ) REFERENCES " + User.TABLE_USER + "( " + User.COLUMN_ID_NUM + " ))";

    public static final String CREATE_TABLE_ROUTE = "CREATE TABLE "
            + Route.TABLE_ROUTE + " ("
            + Route.COLUMN_ROUTE_ID + " INTEGER PRIMARY KEY, "
            + Route.COLUMN_ROUTE_NAME + " VARCHAR, "
            + Route.COLUMN_ROUTE_ORIGIN + " VARCHAR, "
            + Route.COLUMN_ROUTE_DESTINATION + " VARCHAR, "
            + Route.COLUMN_ROUTE_DESCRIPTION + " VARCHAR, "
            + Route.COLUMN_ROUTE_LINE + " INTEGER, "
            + " FOREIGN KEY ( " + Route.COLUMN_ROUTE_LINE + " ) REFERENCES " + Line.TABLE_LINE + "( " + Line.COLUMN_LINE_NUM + " ))";

    public static final String CREATE_TABLE_ROUTE_STOP = "CREATE TABLE "
            + RouteStop.TABLE_ROUTE_STOP + " ("
            + RouteStop.COLUMN_STOP_NUM + " VARCHAR PRIMARY KEY, "
            + RouteStop.COLUMN_ORDER + " INTEGER, "
            + RouteStop.COLUMN_ROUTE_STOP_STOP + " INTEGER, "
            + RouteStop.COLUMN_ROUTE_STOP_ROUTE + " INTEGER, "
            + " FOREIGN KEY ( " + RouteStop.COLUMN_ROUTE_STOP_STOP + " ) REFERENCES " + Stop.TABLE_STOP + "( " + COLUMN_STOP_ID + " ), "
            + " FOREIGN KEY ( " + RouteStop.COLUMN_ROUTE_STOP_ROUTE + " ) REFERENCES " + Route.TABLE_ROUTE + "( " + Route.COLUMN_ROUTE_ID + " ))";

    public static final String CREATE_TABLE_STOP = "CREATE TABLE "
            + Stop.TABLE_STOP + " ("
            + COLUMN_STOP_ID + " INTEGER PRIMARY KEY, "
            + Stop.COLUMN_STOP_NAME + " VARCHAR, "
            + Stop.COLUMN_LATITUDE + " VARCHAR, "
            + Stop.COLUMN_LONGITUDE + " VARCHAR )";

    public static final String CREATE_TABLE_TRIP = "CREATE TABLE "
            + TABLE_TRIP + " ("
            + Trip.COLUMN_TRIP_ID + " INTEGER PRIMARY KEY, "
            + Trip.COLUMN_REMARKS + " VARCHAR, "
            + Trip.COLUMN_TRIP_DATE + " DATETIME, "
            + Trip.COLUMN_DEP_TIME + " DATETIME, "
            + Trip.COLUMN_ARRIVAL_TIME + " DATETIME, "
            + Trip.COLUMN_DURATION + " DOUBLE, "
            + Trip.COLUMN_IS_SPECIAL + " BOOLEAN, "
            + Trip.COLUMN_SP_NUM_PASS + " INTEGER, "
            + Trip.COLUMN_PURPOSE + " VARCHAR, "
            + Trip.COLUMN_TRIP_VEHICLE + " VARCHAR, "
            + Trip.COLUMN_TRIP_DRIVER + " INTEGER, "
            + Trip.COLUMN_TRIP_TRIP_SCHED + " INTEGER, "
            + " FOREIGN KEY ( " + Trip.COLUMN_TRIP_VEHICLE + " ) REFERENCES " + TABLE_VEHICLE + "( " + Vehicle.COLUMN_VEHICLE_ID + " ), "
            + " FOREIGN KEY ( " + Trip.COLUMN_TRIP_DRIVER + " ) REFERENCES " + Driver.TABLE_DRIVER + "( " + Driver.COLUMN_DRIVER_ID + " ), "
            + " FOREIGN KEY ( " + Trip.COLUMN_TRIP_TRIP_SCHED + " ) REFERENCES " + TripSched.TABLE_TRIP_SCHED + "( " + TripSched.COLUMN_TRIP_SCHED_ID + " ))";

    public static final String CREATE_TABLE_TRIP_SCHED = "CREATE TABLE "
            + TABLE_TRIP_SCHED + " ("
            + TripSched.COLUMN_TRIP_SCHED_ID + " INTEGER PRIMARY KEY, "
            + TripSched.COLUMN_TRIP_NUM + " VARCHAR, "
            + TripSched.COLUMN_DEP_TIME + " DATETIME, "
            + TripSched.COLUMN_TRIP_SCHED_ROUTE + " VARCHAR, "
            + " FOREIGN KEY ( " + TripSched.COLUMN_TRIP_SCHED_ROUTE + " ) REFERENCES " + Route.TABLE_ROUTE + "( " + Route.COLUMN_ROUTE_ID + " ))";


    public static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + " ("
            + User.COLUMN_ID_NUM + " INTEGER PRIMARY KEY, "
            + User.COLUMN_NAME + " VARCHAR, "
            + User.COLUMN_EMAIL + " VARCHAR, "
            + User.COLUMN_EMERGENCY_CONTACT + " VARCHAR, "
            + User.COLUMN_EMERGENCY_CONTACT_NUM + " VARCHAR, "
            + User.COLUMN_IS_ADMIN + " BOOLEAN, "
            + User.COLUMN_ADMIN_PASSWORD + " VARCHAR,"
            + User.COLUMN_AP_PRIORITY_ID + " INTEGER )";

    public static final String CREATE_TABLE_VEHICLE = "CREATE TABLE "
            + TABLE_VEHICLE + " ("
            + Vehicle.COLUMN_VEHICLE_ID + " VARCHAR PRIMARY KEY, "
            + Vehicle.COLUMN_VEHICLE_TYPE + " VARCHAR, "
            + Vehicle.COLUMN_CAPACITY + " INTEGER, "
            + Vehicle.COLUMN_IMAGE + " VARCHAR, "
            + Vehicle.COLUMN_PLATE_NUM + " VARCHAR, "
            + Vehicle.COLUMN_MODEL + " VARCHAR, "
            + Vehicle.COLUMN_BRAND + " VARCHAR )";

    public static final String CREATE_TABLE_LANDING = "CREATE TABLE "
            + TABLE_LANDING + " ("
            + Landing.COLUMN_LANDING_ID + " INTEGER PRIMARY KEY, "
            + Landing.COLUMN_LANDING_PLATE_NUM + " VARCHAR, "
            + Landing.COLUMN_LANDING_DRIVER + " VARCHAR )";

    // delete
    public static final String DELETE_DRIVER = "DROP TABLE IF EXISTS " + Driver.TABLE_DRIVER;
    public static final String DELETE_LINE = "DROP TABLE IF EXISTS " + Line.TABLE_LINE;
    public static final String DELETE_PASSENGER = "DROP TABLE IF EXISTS " + Passenger.TABLE_PASSENGER;
    public static final String DELETE_RESERVATION = "DROP TABLE IF EXISTS " + TABLE_RESERVATION;
    public static final String DELETE_ROUTE = "DROP TABLE IF EXISTS " + Route.TABLE_ROUTE;
    public static final String DELETE_ROUTE_STOP = "DROP TABLE IF EXISTS " + RouteStop.TABLE_ROUTE_STOP;
    public static final String DELETE_STOP = "DROP TABLE IF EXISTS " + Stop.TABLE_STOP;
    public static final String DELETE_TRIP = "DROP TABLE IF EXISTS " + TABLE_TRIP;
    public static final String DELETE_TRIP_SCHED = "DROP TABLE IF EXISTS " + TABLE_TRIP_SCHED;
    public static final String DELETE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    public static final String DELETE_VEHICLE = "DROP TABLE IF EXISTS " + TABLE_VEHICLE;
    public static final String DELETE_LANDING = "DROP TABLE IF EXISTS " + TABLE_LANDING;

}