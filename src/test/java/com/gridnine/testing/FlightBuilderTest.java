package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightBuilderTest {
    public static List<Flight> createFlightDepartureTimeBeforeNow(){
        LocalDateTime testDate = LocalDateTime.now();
        return Arrays.asList(createFlight(testDate.minusDays(2), testDate.minusDays(1)));
    }

    public static List<Flight> createFlightArriveTimeBeforeDepart(){
        LocalDateTime testDate = LocalDateTime.now();
        return Arrays.asList(createFlight(testDate.plusHours(2), testDate.minusHours(1)));
    }

    public static List<Flight> createTransferTimeMoreThanTwoHours(){
        LocalDateTime testDate = LocalDateTime.now();
        return Arrays.asList(createFlight(testDate.plusHours(2), testDate.plusHours(4), testDate.plusHours(8), testDate.plusHours(9)));
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
