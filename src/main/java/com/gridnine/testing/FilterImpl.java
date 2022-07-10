package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FilterImpl implements Filter {
    private DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("dd-MM-yyyy 'T' HH:mm");
    private LocalDateTime ldtDepart;
    private LocalDateTime ldtArrive;
    private List<Flight> temp = new ArrayList<>();


    @Override
    public void showAllFlights(List<Flight> flightList) {
        for (int i = 0; i < flightList.size(); i++) {
            System.out.println("Самолет №" + i);
            System.out.println("Количество полетов: " + flightList.get(i).getSegments().size());
            for (int j = 0; j < flightList.get(i).getSegments().size(); j++) {
                LocalDateTime departure = flightList.get(i).getSegments().get(j).getDepartureDate();
                LocalDateTime arrive = flightList.get(i).getSegments().get(j).getArrivalDate();
                System.out.println("Время вылета: " + departure.format(fmt) + " Время прилета: " + arrive.format(fmt));
            }
        }
    }

    @Override
    public List<Flight> departureTimeBeforeNow(List<Flight> flightList) {
        temp.clear();
        temp.addAll(flightList);
        for (int i = 0; i < flightList.size(); i++) {
            for (int j = 0; j < flightList.get(i).getSegments().size(); j++) {
                ldtDepart = flightList.get(i).getSegments().get(j).getDepartureDate();
                if (LocalDateTime.now().isAfter(ldtDepart)) {
                    temp.remove(i);
                }
            }
        }
        showAllFlights(temp);
        return temp;
    }

    @Override
    public List<Flight> arriveTimeBeforeDeparture(List<Flight> flightList) {
        temp.clear();
        temp.addAll(flightList);
        for (int i = 0; i < flightList.size(); i++) {
            for (int j = 0; j < flightList.get(i).getSegments().size(); j++) {
                ldtDepart = flightList.get(i).getSegments().get(j).getDepartureDate();
                ldtArrive = flightList.get(i).getSegments().get(j).getArrivalDate();
                if (ldtArrive.isBefore(ldtDepart)) {
                    temp.remove(i);
                }
            }
        }
        showAllFlights(temp);
        return temp;
    }

    @Override
    public List<Flight> groundTimeMoreThanTwoHours(List<Flight> flightList) {
        temp.clear();
        temp.addAll(flightList);
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getSegments().size() > 1) {
                for (int j = 0; j < flightList.get(i).getSegments().size() - 1; j++) {
                    ldtDepart = flightList.get(i).getSegments().get(j + 1).getDepartureDate();
                    ldtArrive = flightList.get(i).getSegments().get(j).getArrivalDate();
                    if (ldtDepart.isAfter(ldtArrive.plusHours(2))) {
                        temp.remove(i);
                    }
                }
            }
        }
        showAllFlights(temp);
       return temp;
    }
}

