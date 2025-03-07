package info.skytag.apiserver.flight.service;

import info.skytag.apiserver.flight.model.dto.FlightDto;

import java.time.LocalDate;
import java.util.List;

public interface FlightDataService {
    long getTotalDepartureSize(LocalDate searchDate);
    List<FlightDto> getDepartureListByDate(int pageNo, int numOfRows, LocalDate searchDate);
    long getTotalArrivalSize(LocalDate searchDate);
    List<FlightDto> getArrivalListByDate(int pageNo, int numOfRows, LocalDate searchDate);
    void saveAllArrivals(List<FlightDto> arrivals);
    void fetchAllArrivals(LocalDate searchDate);

    void saveAllDepartures(List<FlightDto> departures);
    void fetchAllDepartures(LocalDate searchDate);
}
