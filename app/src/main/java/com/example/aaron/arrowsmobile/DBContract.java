package com.example.aaron.arrowsmobile;

import android.provider.BaseColumns;

import static com.example.aaron.arrowsmobile.DBContract.Local.TABLE_LOCAL;
import static com.example.aaron.arrowsmobile.DBContract.Reservation.TABLE_RESERVATION;
import static com.example.aaron.arrowsmobile.DBContract.Stop.COLUMN_STOP_ID;
import static com.example.aaron.arrowsmobile.DBContract.Trip.TABLE_TRIP;
import static com.example.aaron.arrowsmobile.DBContract.TripSched.TABLE_TRIP_SCHED;
import static com.example.aaron.arrowsmobile.DBContract.TripVehicleAssignment.TABLE_TRIP_VEHICLE_ASSIGNMENT;
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
        public static final String COLUMN_STATUS_CODE = "status_code";
    }

    public static class Line implements BaseColumns{
        public static final String TABLE_LINE = "line";
        public static final String COLUMN_LINE_NUM = "line_num";
        public static final String COLUMN_LINE_NAME = "line_name";
        public static final String COLUMN_STATUS_CODE = "status_code";
    }

    public static class Passenger implements BaseColumns{
        public static final String TABLE_PASSENGER = "passenger";
        public static final String COLUMN_PASSENGER_ID = "passenger_id";
        public static final String COLUMN_TAP_IN = "tap_in";
        public static final String COLUMN_TAP_OUT = "tap_out";
        public static final String COLUMN_DISEMBARKATION_PT = "disembarkation_pt";
        public static final String COLUMN_DESTINATION = "destination";
        public static final String COLUMN_RESERVATION_NUM = "reservation_num";
        public static final String COLUMN_VEHICLE_ID = "vehicle_id";
        public static final String COLUMN_DRIVER_ID = "driver_id";
    }

    public static class Reservation implements BaseColumns{
        public static final String TABLE_RESERVATION = "reservation";
        public static final String COLUMN_RESERVATION_NUM = "reservation_num";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_DESTINATION = "destination";
        public static final String COLUMN_TRIP_ID = "trip_id";
        public static final String COLUMN_ID_NUM = "id_num";
        public static final String COLUMN_STOP_NUM = "stop_num";
        public static final String COLUMN_STATUS_CODE = "status_code";
    }

    public static class Route implements BaseColumns{
        public static final String TABLE_ROUTE = "route";
        public static final String COLUMN_ROUTE_ID = "route_id";
        public static final String COLUMN_ROUTE_ORIGIN = "route_origin";
        public static final String COLUMN_ROUTE_DESTINATION = "route_destination";
        public static final String COLUMN_ROUTE_DESCRIPTION = "route_description";
        public static final String COLUMN_LINE_NUM = "line_num";
    }

    public static class RouteStop implements BaseColumns{
        public static final String TABLE_ROUTE_STOP = "route_stop";
        public static final String COLUMN_STOP_NUM = "stop_num";
        public static final String COLUMN_ORDER = "stop_order";
        public static final String COLUMN_STOP_ID = "stop_id";
        public static final String COLUMN_ROUTE_ID = "route_id";
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
        public static final String COLUMN_TRIP_DATE = "trip_date";
        public static final String COLUMN_DEP_TIME = "dep_time";
        public static final String COLUMN_ARRIVAL_TIME = "arrival_time";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_TRIP_SCHED_ID = "trip_sched_id";
        public static final String COLUMN_STATUS_CODE = "status_code";
    }

    public static class TripVehicleAssignment implements BaseColumns{
        public static final String TABLE_TRIP_VEHICLE_ASSIGNMENT = "trip_vehicle_assignment";
        public static final String COLUMN_ASSIGN_ID = "assign_id";
        public static final String COLUMN_TRIP_ID = "trip_id";
        public static final String COLUMN_VEHICLE_ID = "vehicle_id";
        public static final String COLUMN_DRIVER_ID = "driver_id";
        public static final String COLUMN_STATUS_CODE = "status_code";
    }

    public static class TripSched implements BaseColumns{
        public static final String TABLE_TRIP_SCHED = "trip_sched";
        public static final String COLUMN_TRIP_SCHED_ID = "trip_sched_id";
        public static final String COLUMN_TRIP_NUM = "trip_num";
        public static final String COLUMN_DEP_TIME = "dep_time";
        public static final String COLUMN_ROUTE_ID = "route_id";
    }

    public static class User implements BaseColumns{
        public static final String TABLE_USER = "user";
        public static final String COLUMN_ID_NUM = "id_num";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_STATUS_CODE = "status_code";
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
        public static final String COLUMN_STATUS_CODE = "status_code";
    }

    // app-only table
    public static class Local implements BaseColumns{
        public static final String TABLE_LOCAL = "local";
        public static final String COLUMN_LOCAL_ID = "local_id";
        public static final String COLUMN_LOCAL_PLATE_NUM = "local_plate_num";
        public static final String COLUMN_LOCAL_DRIVER = "local_plate_driver";
        public static final String COLUMN_LOCAL_DATE = "local_date";
    }

    // create

    public static final String CREATE_TABLE_DRIVER = "CREATE TABLE "
            + Driver.TABLE_DRIVER + " ("
            + Driver.COLUMN_DRIVER_ID + " VARCHAR PRIMARY KEY, "
            + Driver.COLUMN_LAST_NAME + " VARCHAR, "
            + Driver.COLUMN_FIRST_NAME + " VARCHAR, "
            + Driver.COLUMN_NICKNAME + " VARCHAR, "
            + Driver.COLUMN_STATUS_CODE + " INTEGER )";

    public static final String CREATE_TABLE_LINE = "CREATE TABLE "
            + Line.TABLE_LINE + " ("
            + Line.COLUMN_LINE_NUM + " INTEGER PRIMARY KEY, "
            + Line.COLUMN_STATUS_CODE + " INTEGER, "
            + Line.COLUMN_LINE_NAME + " VARCHAR )";

    public static final String CREATE_TABLE_PASSENGER = "CREATE TABLE "
            + Passenger.TABLE_PASSENGER + " ("
            + Passenger.COLUMN_PASSENGER_ID + " INTEGER PRIMARY KEY, "
            + Passenger.COLUMN_TAP_IN + " DATETIME, "
            + Passenger.COLUMN_TAP_OUT + " DATETIME, "
            + Passenger.COLUMN_DISEMBARKATION_PT + " VARCHAR, "
            + Passenger.COLUMN_DESTINATION + " VARCHAR, "
            + Passenger.COLUMN_RESERVATION_NUM + " INTEGER, "
            + Passenger.COLUMN_VEHICLE_ID + " VARCHAR, "
            + Passenger.COLUMN_DRIVER_ID + " VARCHAR, "
            + " FOREIGN KEY ( " + Passenger.COLUMN_RESERVATION_NUM + " ) REFERENCES " + Reservation.TABLE_RESERVATION + "( " + Reservation.COLUMN_RESERVATION_NUM + " ) ON DELETE CASCADE,"
            + " FOREIGN KEY ( " + Passenger.COLUMN_VEHICLE_ID + " ) REFERENCES " + Vehicle.TABLE_VEHICLE + "( " + Vehicle.COLUMN_VEHICLE_ID + " ),"
            + " FOREIGN KEY ( " + Passenger.COLUMN_DRIVER_ID + " ) REFERENCES " + Driver.TABLE_DRIVER + "( " + Driver.COLUMN_DRIVER_ID + " ))";

    public static final String CREATE_TABLE_RESERVATION = "CREATE TABLE "
            + TABLE_RESERVATION + " ("
            + Reservation.COLUMN_RESERVATION_NUM + " VARCHAR PRIMARY KEY, "
            + Reservation.COLUMN_TIMESTAMP + " DATETIME, "
            + Reservation.COLUMN_DESTINATION + " VARCHAR, "
            + Reservation.COLUMN_TRIP_ID + " INTEGER, "
            + Reservation.COLUMN_STOP_NUM + " VARCHAR, "
            + Reservation.COLUMN_ID_NUM + " INTEGER,"
            + Reservation.COLUMN_STATUS_CODE + " INTEGER, "
            + " FOREIGN KEY ( " + Reservation.COLUMN_TRIP_ID + " ) REFERENCES " + Trip.TABLE_TRIP + "( " + Trip.COLUMN_TRIP_ID + " ),"
            + " FOREIGN KEY ( " + Reservation.COLUMN_STOP_NUM + " ) REFERENCES " + RouteStop.TABLE_ROUTE_STOP + "( " + RouteStop.COLUMN_STOP_NUM + " ),"
            + " FOREIGN KEY ( " + Reservation.COLUMN_ID_NUM + " ) REFERENCES " + User.TABLE_USER + "( " + User.COLUMN_ID_NUM + " ))";

    public static final String CREATE_TABLE_ROUTE = "CREATE TABLE "
            + Route.TABLE_ROUTE + " ("
            + Route.COLUMN_ROUTE_ID + " INTEGER PRIMARY KEY, "
            + Route.COLUMN_ROUTE_ORIGIN + " VARCHAR, "
            + Route.COLUMN_ROUTE_DESTINATION + " VARCHAR, "
            + Route.COLUMN_ROUTE_DESCRIPTION + " VARCHAR, "
            + Route.COLUMN_LINE_NUM + " INTEGER, "
            + " FOREIGN KEY ( " + Route.COLUMN_LINE_NUM + " ) REFERENCES " + Line.TABLE_LINE + "( " + Line.COLUMN_LINE_NUM + " ))";

    public static final String CREATE_TABLE_ROUTE_STOP = "CREATE TABLE "
            + RouteStop.TABLE_ROUTE_STOP + " ("
            + RouteStop.COLUMN_STOP_NUM + " VARCHAR PRIMARY KEY, "
            + RouteStop.COLUMN_ORDER + " INTEGER, "
            + RouteStop.COLUMN_STOP_ID + " INTEGER, "
            + RouteStop.COLUMN_ROUTE_ID + " INTEGER, "
            + " FOREIGN KEY ( " + RouteStop.COLUMN_STOP_ID + " ) REFERENCES " + Stop.TABLE_STOP + "( " + COLUMN_STOP_ID + " ), "
            + " FOREIGN KEY ( " + RouteStop.COLUMN_ROUTE_ID + " ) REFERENCES " + Route.TABLE_ROUTE + "( " + Route.COLUMN_ROUTE_ID + " ))";

    public static final String CREATE_TABLE_STOP = "CREATE TABLE "
            + Stop.TABLE_STOP + " ("
            + COLUMN_STOP_ID + " INTEGER PRIMARY KEY, "
            + Stop.COLUMN_STOP_NAME + " VARCHAR, "
            + Stop.COLUMN_LATITUDE + " VARCHAR, "
            + Stop.COLUMN_LONGITUDE + " VARCHAR )";

    public static final String CREATE_TABLE_TRIP = "CREATE TABLE "
            + TABLE_TRIP + " ("
            + Trip.COLUMN_TRIP_ID + " INTEGER PRIMARY KEY, "
            + Trip.COLUMN_TRIP_DATE + " DATETIME, "
            + Trip.COLUMN_DEP_TIME + " DATETIME, "
            + Trip.COLUMN_ARRIVAL_TIME + " DATETIME, "
            + Trip.COLUMN_DURATION + " DOUBLE, "
            + Trip.COLUMN_STATUS_CODE + " INTEGER, "
            + Trip.COLUMN_TRIP_SCHED_ID + " INTEGER, "
            + " FOREIGN KEY ( " + Trip.COLUMN_TRIP_SCHED_ID + " ) REFERENCES " + TripSched.TABLE_TRIP_SCHED + "( " + TripSched.COLUMN_TRIP_SCHED_ID + " ))";

    public static final String CREATE_TABLE_TRIP_VEHICLE_ASSIGNMENT = "CREATE TABLE "
            + TABLE_TRIP_VEHICLE_ASSIGNMENT + " ("
            + TripVehicleAssignment.COLUMN_ASSIGN_ID + " INTEGER PRIMARY KEY, "
            + TripVehicleAssignment.COLUMN_STATUS_CODE + " INTEGER, "
            + TripVehicleAssignment.COLUMN_TRIP_ID + " INTEGER, "
            + TripVehicleAssignment.COLUMN_VEHICLE_ID + " VARCHAR, "
            + TripVehicleAssignment.COLUMN_DRIVER_ID + " VARCHAR, "
            + " FOREIGN KEY ( " + TripVehicleAssignment.COLUMN_TRIP_ID + " ) REFERENCES " + Trip.TABLE_TRIP + "( " + Trip.COLUMN_TRIP_ID + " ), "
            + " FOREIGN KEY ( " + TripVehicleAssignment.COLUMN_VEHICLE_ID + " ) REFERENCES " + Vehicle.TABLE_VEHICLE + "( " + Vehicle.COLUMN_VEHICLE_ID + " ), "
            + " FOREIGN KEY ( " + TripVehicleAssignment.COLUMN_DRIVER_ID + " ) REFERENCES " + Driver.TABLE_DRIVER + "( " + Driver.COLUMN_DRIVER_ID + " ))";

    public static final String CREATE_TABLE_TRIP_SCHED = "CREATE TABLE "
            + TABLE_TRIP_SCHED + " ("
            + TripSched.COLUMN_TRIP_SCHED_ID + " INTEGER PRIMARY KEY, "
            + TripSched.COLUMN_TRIP_NUM + " VARCHAR, "
            + TripSched.COLUMN_DEP_TIME + " DATETIME, "
            + TripSched.COLUMN_ROUTE_ID + " INTEGER, "
            + " FOREIGN KEY ( " + TripSched.COLUMN_ROUTE_ID + " ) REFERENCES " + Route.TABLE_ROUTE + "( " + Route.COLUMN_ROUTE_ID + " ))";


    public static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + " ("
            + User.COLUMN_ID_NUM + " INTEGER PRIMARY KEY, "
            + User.COLUMN_NAME + " VARCHAR, "
            + User.COLUMN_STATUS_CODE + " INTEGER )";

    public static final String CREATE_TABLE_VEHICLE = "CREATE TABLE "
            + TABLE_VEHICLE + " ("
            + Vehicle.COLUMN_VEHICLE_ID + " VARCHAR PRIMARY KEY, "
            + Vehicle.COLUMN_VEHICLE_TYPE + " VARCHAR, "
            + Vehicle.COLUMN_CAPACITY + " INTEGER, "
            + Vehicle.COLUMN_IMAGE + " VARCHAR, "
            + Vehicle.COLUMN_PLATE_NUM + " VARCHAR, "
            + Vehicle.COLUMN_MODEL + " VARCHAR, "
            + Vehicle.COLUMN_BRAND + " VARCHAR, "
            + Vehicle.COLUMN_STATUS_CODE + " INTEGER )";

    public static final String CREATE_TABLE_LANDING = "CREATE TABLE "
            + Local.TABLE_LOCAL + " ("
            + Local.COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY, "
            + Local.COLUMN_LOCAL_PLATE_NUM + " VARCHAR, "
            + Local.COLUMN_LOCAL_DRIVER + " VARCHAR, "
            + Local.COLUMN_LOCAL_DATE + " VARCHAR )";

    // delete
    public static final String DELETE_DRIVER = "DROP TABLE IF EXISTS " + Driver.TABLE_DRIVER;
    public static final String DELETE_LINE = "DROP TABLE IF EXISTS " + Line.TABLE_LINE;
    public static final String DELETE_PASSENGER = "DROP TABLE IF EXISTS " + Passenger.TABLE_PASSENGER;
    public static final String DELETE_RESERVATION = "DROP TABLE IF EXISTS " + TABLE_RESERVATION;
    public static final String DELETE_ROUTE = "DROP TABLE IF EXISTS " + Route.TABLE_ROUTE;
    public static final String DELETE_ROUTE_STOP = "DROP TABLE IF EXISTS " + RouteStop.TABLE_ROUTE_STOP;
    public static final String DELETE_STOP = "DROP TABLE IF EXISTS " + Stop.TABLE_STOP;
    public static final String DELETE_TRIP = "DROP TABLE IF EXISTS " + TABLE_TRIP;
    public static final String DELETE_TRIP_VEHICLE_ASSIGNMENT = "DROP TABLE IF EXISTS " + TABLE_TRIP_VEHICLE_ASSIGNMENT;
    public static final String DELETE_TRIP_SCHED = "DROP TABLE IF EXISTS " + TABLE_TRIP_SCHED;
    public static final String DELETE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    public static final String DELETE_VEHICLE = "DROP TABLE IF EXISTS " + TABLE_VEHICLE;
    public static final String DELETE_LANDING = "DROP TABLE IF EXISTS " + TABLE_LOCAL;

}