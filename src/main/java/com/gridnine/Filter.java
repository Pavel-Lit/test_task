package com.gridnine;

import java.util.List;

public interface Filter {
    void showAllFlights(List<Flight> flightList);

    void departureTimeBeforeNow(List<Flight> flightList);

    void segmentWithArriveTimeBeforeDeparture(List<Flight> flightList);

    void groundTimeMoreThanTwoHours(List<Flight> flightList);
}
