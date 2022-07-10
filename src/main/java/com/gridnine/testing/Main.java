package com.gridnine.testing;

public class Main {
    public static void main(String[] args) {
        FilterImpl filter = new FilterImpl();
        filter.showAllFlights(FlightBuilder.createFlights());
        System.out.println("\nВылеты на сегодняшний день");
        filter.departureTimeBeforeNow(FlightBuilder.createFlights());
        System.out.println("\nВремя вылета раньше времени прилета");
        filter.arriveTimeBeforeDeparture(FlightBuilder.createFlights());
        System.out.println("\nПересадка не более двух часов");
        filter.groundTimeMoreThanTwoHours(FlightBuilder.createFlights());

    }
}
