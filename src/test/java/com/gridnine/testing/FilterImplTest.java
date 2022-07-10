package com.gridnine.testing;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class FilterImplTest {
    private FilterImpl filter = new FilterImpl();


    @Test
    void departureTimeBeforeNow() {
        Assertions.assertEquals(0, filter.departureTimeBeforeNow(FlightBuilderTest.createFlightDepartureTimeBeforeNow()).size());
    }

    @Test
    void arriveTimeBeforeDeparture() {
        Assertions.assertEquals(0, filter.arriveTimeBeforeDeparture(FlightBuilderTest.createFlightArriveTimeBeforeDepart()).size());
    }

    @Test
    void groundTimeMoreThanTwoHours() {
        Assertions.assertEquals(0, filter.groundTimeMoreThanTwoHours(FlightBuilderTest.createTransferTimeMoreThanTwoHours()).size());
    }
}