package com.gridnine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FilterImpl implements Filter {
    private DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("dd-MM-yyyy 'T' HH:mm");

    @Override
    public void showAllFlights(List<Flight> flightList) {
        for (int i = 0; i < flightList.size(); i++) {
            System.out.println("Самолет №" + i );
            System.out.println("Количество полетов: "+flightList.get(i).getSegments().size());
            for (int j = 0; j < flightList.get(i).getSegments().size(); j++) {
                showFlight(flightList, i, j);
            }
        }
    }

    private void showFlight(List<Flight> flightList, int i, int j) {
        LocalDateTime departure = flightList.get(i).getSegments().get(j).getDepartureDate();
        LocalDateTime arrive = flightList.get(i).getSegments().get(j).getArrivalDate();
        System.out.println("Время вылета: "+ departure.format(fmt)+ " Время прилета: "+arrive.format(fmt));
    }

    @Override
    public void departureTimeBeforeNow(List<Flight> flightList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ldtDepart = null;
        for (int i = 0; i < flightList.size(); i++) {
            for (int j = 0; j < flightList.get(i).getSegments().size(); j++) {
                ldtDepart = flightList.get(i).getSegments().get(j).getDepartureDate();
                if (now.isAfter(ldtDepart)) {
                    System.out.println("Самолет №" + i + " вылетает раньше текущего времени");
                    showFlight(flightList, i, j);
                }
            }
        }
    }

    @Override
    public void segmentWithArriveTimeBeforeDeparture(List<Flight> flightList) {
        LocalDateTime ldtArrive = null;
        LocalDateTime ldtDepart = null;
        for (int i = 0; i < flightList.size(); i++) {
            for (int j = 0; j < flightList.get(i).getSegments().size(); j++) {
                ldtDepart = flightList.get(i).getSegments().get(j).getDepartureDate();
                ldtArrive = flightList.get(i).getSegments().get(j).getArrivalDate();
                if (ldtArrive.isBefore(ldtDepart)) {
                    System.out.println("Самолет №" + i + " прилетает раньше вылета");
                    showFlight(flightList, i, j);
                }
            }

        }


    }

    @Override
    public void groundTimeMoreThanTwoHours(List<Flight> flightList) {
        LocalDateTime ldtArrive = null;
        LocalDateTime ldtDepart = null;
        for (int i = 0; i < flightList.size(); i++) {
            for (int j = 0; j < flightList.get(i).getSegments().size()-1; j++) {
                ldtDepart = flightList.get(i).getSegments().get(j+1).getDepartureDate();
                ldtArrive = flightList.get(i).getSegments().get(j).getArrivalDate();
                if (ldtDepart.isAfter(ldtArrive.plusHours(2))) {
                    System.out.println("Самолет №" + i + " имеет пересадку больше 2-х часов");
                    showFlight(flightList, i, j);
                }
            }

        }
    }
}
