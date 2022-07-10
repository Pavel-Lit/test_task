package com.gridnine.testing;

import java.util.List;

public interface Filter {
    void showAllFlights(List<Flight> flightList);

    List<Flight> departureTimeBeforeNow(List<Flight> flightList);

    List<Flight> arriveTimeBeforeDeparture(List<Flight> flightList);

    List<Flight> groundTimeMoreThanTwoHours(List<Flight> flightList);
}
