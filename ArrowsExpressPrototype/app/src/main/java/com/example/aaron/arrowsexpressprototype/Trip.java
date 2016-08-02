package com.example.aaron.arrowsexpressprototype;

import java.util.ArrayList;

// Instantiated whenever a trip ends at either DLSU or STC campus
public class Trip {

    private ArrayList<ServiceEvent> serviceEvents;

    public Trip(ArrayList<ServiceEvent> serviceEvents) {
        this.serviceEvents = serviceEvents;
    }

    public ArrayList<ServiceEvent> getServiceEvents() {
        return serviceEvents;
    }

    public void addServiceEvent(ServiceEvent event){
        serviceEvents.add(event);
    }
}
