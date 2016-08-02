package com.example.aaron.arrowsexpressprototype;

import java.util.Calendar;

// Instantiated whenever a student gets on or off the service
public class ServiceEvent {

    private String studentID;
    private String area;
    private Calendar calendar;

    public ServiceEvent(String area, Calendar calendar, String studentID) {
        this.studentID = studentID;
        this.area = area;
        this.calendar = calendar;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getArea() {
        return area;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
