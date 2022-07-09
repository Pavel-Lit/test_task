package com.gridnine;

public class Main {
    public static void main(String[] args) {
        FilterImpl filter = new FilterImpl();
//        filter.showAllFlights(FlightBuilder.createFlights());
        System.out.println("_______________________________________");
        filter.departureTimeBeforeNow(FlightBuilder.createFlights());
        System.out.println("_______________________________________");
        filter.segmentWithArriveTimeBeforeDeparture(FlightBuilder.createFlights());
        System.out.println("_______________________________________");
        filter.groundTimeMoreThanTwoHours(FlightBuilder.createFlights());

    }
}
