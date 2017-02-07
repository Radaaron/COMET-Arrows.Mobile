package com.example.aaron.arrowsmobile;

import android.provider.BaseColumns;

import static com.example.aaron.arrowsmobile.DBContract.Stop.COLUMN_STOP_ID;
import static com.example.aaron.arrowsmobile.DBContract.Trip.TABLE_TRIP;
import static com.example.aaron.arrowsmobile.DBContract.TripSched.TABLE_TRIP_SCHED;

public final class DBContract {
    // Contract class for database
    // private constructor to avoid instantiation
    private DBContract(){}

    public static final String DB_NAME = "arrowsDB.db";

    // db-entity classes (alphabetical)
    public static class AcademicPeriod implements BaseColumns{
        public static final String TABLE_ACADEMIC_PERIOD = "academic_period";
        public static final String COLUMN_PERIOD_ID = "period_id";
        public static final String COLUMN_PERIOD = "period";
        public static final String COLUMN_AY_START = "AY_start";
        public static final String COLUMN_AY_END = "AY_end";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_END_DATE = "end_date";
    }

    public static class CanDrive implements BaseColumns{
        public static final String TABLE_CAN_DRIVE = "can_drive";
        public static final String COLUMN_MODEL = "model";
    }

    public static class ClassAssignment implements BaseColumns{
        public static final String TABLE_CLASS_ASSIGNMENT = "class_assignment";
    }

    public static class ClassSchedule implements BaseColumns{
        public static final String TABLE_CLASS_SCHEDULE = "class_schedule";
        public static final String COLUMN_SCHED_ID = "sched_id";
        public static final String COLUMN_AY = "AY";
        public static final String COLUMN_COURSE_CODE = "course_code";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_TERM = "term";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_SECTION = "section";
        public static final String COLUMN_ROOM = "room";
    }

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
        public static final String COLUMN_IS_CHANCE = "is_chance";
        public static final String COLUMN_PASSENGER_TRIP = "trip";
    }

    public static class Reservation implements BaseColumns{
        public static final String TABLE_RESERVATION = "reservation";
        public static final String COLUMN_RESERVATION_NUM = "reservation_num";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_DESTINATION = "destination";
        public static final String COLUMN_REMARK = "remark";
    }

    public static class Route implements BaseColumns{
        public static final String TABLE_ROUTE = "route";
        public static final String COLUMN_ROUTE_ID = "route_id";
        public static final String COLUMN_ROUTE_NAME = "route_name";
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

    public static class Status implements BaseColumns{
        public static final String TABLE_STATUS = "status";
        public static final String COLUMN_STATUS_CODE = "status_code";
        public static final String COLUMN_STATUS_NAME = "status_name";
        public static final String COLUMN_STATUS_TYPE = "status_type";
    }

    public static class Stop implements BaseColumns{
        public static final String TABLE_STOP = "stop";
        public static final String COLUMN_STOP_ID = "stop_id";
        public static final String COLUMN_STOP_NAME = "stop_name";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }

    public static class SystemConfig implements BaseColumns{
        public static final String TABLE_SYSTEM_CONFIG = "system_config";
        public static final String COLUMN_CONFIG_ID = "config_id";
        public static final String COLUMN_CONFIG_NAME = "config_name";
        public static final String COLUMN_CONFIG_VAL = "config_val";
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
    }

    public static class UserType implements BaseColumns{
        public static final String TABLE_USER_TYPE = "user_type";
        public static final String COLUMN_TYPE_ID = "type_id";
        public static final String COLUMN_TYPE_NAME = "type_name";
        public static final String COLUMN_TYPE_PRIORITY = "type_priority";
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


    // create
    public static final String CREATE_TABLE_ACADEMIC_PERIOD = "CREATE TABLE "
            + AcademicPeriod.TABLE_ACADEMIC_PERIOD + " ("
            + AcademicPeriod.COLUMN_PERIOD_ID + " VARCHAR PRIMARY KEY, "
            + AcademicPeriod.COLUMN_PERIOD + " VARCHAR, "
            + AcademicPeriod.COLUMN_AY_START + " INTEGER, "
            + AcademicPeriod.COLUMN_AY_END + " INTEGER, "
            + AcademicPeriod.COLUMN_START_DATE + " DATETIME, "
            + AcademicPeriod.COLUMN_END_DATE + " DATETIME" + " )";

    public static final String CREATE_TABLE_CAN_DRIVE = "CREATE TABLE "
            + CanDrive.TABLE_CAN_DRIVE + " ("
            + CanDrive.COLUMN_MODEL + " VARCHAR PRIMARY KEY )";

    // cant create table with no columns in SQLite
    // public static final String CREATE_TABLE_CLASS_ASSIGNMENT = "CREATE TABLE " + ClassAssignment.TABLE_CLASS_ASSIGNMENT + "()";

    public static final String CREATE_TABLE_CLASS_SCHEDULE = "CREATE TABLE "
            + ClassSchedule.TABLE_CLASS_SCHEDULE + " ("
            + ClassSchedule.COLUMN_SCHED_ID + " INTEGER PRIMARY KEY, "
            + ClassSchedule.COLUMN_AY + " VARCHAR, "
            + ClassSchedule.COLUMN_COURSE_CODE + " VARCHAR, "
            + ClassSchedule.COLUMN_START_TIME + " DATETIME, "
            + ClassSchedule.COLUMN_END_TIME + " DATETIME, "
            + ClassSchedule.COLUMN_TERM + " INTEGER, "
            + ClassSchedule.COLUMN_DAY + " VARCHAR, "
            + ClassSchedule.COLUMN_SECTION + " VARCHAR, "
            + ClassSchedule.COLUMN_ROOM + " VARCHAR )";

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
            + Passenger.COLUMN_IS_CHANCE + " BOOLEAN, "
            + Passenger.COLUMN_PASSENGER_TRIP + " INTEGER, "
            + " FOREIGN KEY ( " + Passenger.COLUMN_PASSENGER_TRIP + " ) REFERENCES " + TABLE_TRIP + "( " + Trip.COLUMN_TRIP_ID + " ))";

    public static final String CREATE_TABLE_RESERVATION = "CREATE TABLE "
            + Reservation.TABLE_RESERVATION + " ("
            + Reservation.COLUMN_RESERVATION_NUM + " VARCHAR PRIMARY KEY, "
            + Reservation.COLUMN_TIMESTAMP + " DATETIME, "
            + Reservation.COLUMN_DESTINATION + " VARCHAR, "
            + Reservation.COLUMN_REMARK + " VARCHAR )";

    public static final String CREATE_TABLE_ROUTE = "CREATE TABLE "
            + Route.TABLE_ROUTE + " ("
            + Route.COLUMN_ROUTE_ID + " INTEGER PRIMARY KEY, "
            + Route.COLUMN_ROUTE_NAME + " VARCHAR, "
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

    public static final String CREATE_TABLE_STATUS = "CREATE TABLE "
            + Status.TABLE_STATUS + " ("
            + Status.COLUMN_STATUS_CODE + " INTEGER PRIMARY KEY, "
            + Status.COLUMN_STATUS_NAME + " VARCHAR, "
            + Status.COLUMN_STATUS_TYPE + " VARCHAR )";

    public static final String CREATE_TABLE_STOP = "CREATE TABLE "
            + Stop.TABLE_STOP + " ("
            + COLUMN_STOP_ID + " INTEGER PRIMARY KEY, "
            + Stop.COLUMN_STOP_NAME + " VARCHAR, "
            + Stop.COLUMN_LATITUDE + " VARCHAR, "
            + Stop.COLUMN_LONGITUDE + " VARCHAR )";

    public static final String CREATE_TABLE_SYSTEM_CONFIG = "CREATE TABLE "
            + SystemConfig.TABLE_SYSTEM_CONFIG + " ("
            + SystemConfig.COLUMN_CONFIG_ID + " INTEGER PRIMARY KEY, "
            + SystemConfig.COLUMN_CONFIG_NAME + " VARCHAR, "
            + SystemConfig.COLUMN_CONFIG_VAL + " VARCHAR )";

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
            + " FOREIGN KEY ( " + Trip.COLUMN_TRIP_VEHICLE + " ) REFERENCES " + Vehicle.TABLE_VEHICLE + "( " + Vehicle.COLUMN_VEHICLE_ID + " ), "
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
            + User.TABLE_USER + " ("
            + User.COLUMN_ID_NUM + " INTEGER PRIMARY KEY, "
            + User.COLUMN_NAME + " VARCHAR, "
            + User.COLUMN_EMAIL + " VARCHAR, "
            + User.COLUMN_EMERGENCY_CONTACT + " VARCHAR, "
            + User.COLUMN_EMERGENCY_CONTACT_NUM + " VARCHAR, "
            + User.COLUMN_IS_ADMIN + " BOOLEAN, "
            + User.COLUMN_ADMIN_PASSWORD + " VARCHAR )";

    public static final String CREATE_TABLE_USER_TYPE = "CREATE TABLE "
            + UserType.TABLE_USER_TYPE + " ("
            + UserType.COLUMN_TYPE_ID + " INTEGER PRIMARY KEY, "
            + UserType.COLUMN_TYPE_NAME + " VARCHAR, "
            + UserType.COLUMN_TYPE_PRIORITY + " INTEGER )";

    public static final String CREATE_TABLE_VEHICLE = "CREATE TABLE "
            + Vehicle.TABLE_VEHICLE + " ("
            + Vehicle.COLUMN_VEHICLE_ID + " VARCHAR PRIMARY KEY, "
            + Vehicle.COLUMN_VEHICLE_TYPE + " VARCHAR, "
            + Vehicle.COLUMN_CAPACITY + " INTEGER, "
            + Vehicle.COLUMN_IMAGE + " VARCHAR, "
            + Vehicle.COLUMN_PLATE_NUM + " VARCHAR, "
            + Vehicle.COLUMN_MODEL + " VARCHAR, "
            + Vehicle.COLUMN_BRAND + " VARCHAR )";

    // delete
    public static final String DELETE_ACADEMIC_PERIOD = "DROP TABLE IF EXISTS " + AcademicPeriod.TABLE_ACADEMIC_PERIOD;
    public static final String DELETE_CAN_DRIVE = "DROP TABLE IF EXISTS " + CanDrive.TABLE_CAN_DRIVE;
    // public static final String DELETE_CLASS_ASSIGNMENT = "DROP TABLE IF EXISTS " + ClassAssignment.TABLE_CLASS_ASSIGNMENT;
    public static final String DELETE_CLASS_SCHEDULE = "DROP TABLE IF EXISTS " + ClassSchedule.TABLE_CLASS_SCHEDULE;
    public static final String DELETE_DRIVER = "DROP TABLE IF EXISTS " + Driver.TABLE_DRIVER;
    public static final String DELETE_LINE = "DROP TABLE IF EXISTS " + Line.TABLE_LINE;
    public static final String DELETE_PASSENGER = "DROP TABLE IF EXISTS " + Passenger.TABLE_PASSENGER;
    public static final String DELETE_RESERVATION = "DROP TABLE IF EXISTS " + Reservation.TABLE_RESERVATION;
    public static final String DELETE_ROUTE = "DROP TABLE IF EXISTS " + Route.TABLE_ROUTE;
    public static final String DELETE_ROUTE_STOP = "DROP TABLE IF EXISTS " + RouteStop.TABLE_ROUTE_STOP;
    public static final String DELETE_STATUS = "DROP TABLE IF EXISTS " + Status.TABLE_STATUS;
    public static final String DELETE_STOP = "DROP TABLE IF EXISTS " + Stop.TABLE_STOP;
    public static final String DELETE_SYSTEM_CONFIG = "DROP TABLE IF EXISTS " + SystemConfig.TABLE_SYSTEM_CONFIG;
    public static final String DELETE_TRIP = "DROP TABLE IF EXISTS " + TABLE_TRIP;
    public static final String DELETE_TRIP_SCHED = "DROP TABLE IF EXISTS " + TABLE_TRIP_SCHED;
    public static final String DELETE_USER = "DROP TABLE IF EXISTS " + User.TABLE_USER;
    public static final String DELETE_USER_TYPE = "DROP TABLE IF EXISTS " + UserType.TABLE_USER_TYPE;
    public static final String DELETE_VEHICLE = "DROP TABLE IF EXISTS " + Vehicle.TABLE_VEHICLE;

}
