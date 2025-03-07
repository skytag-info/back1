package info.skytag.apiserver.flight.service.cache;


import info.skytag.apiserver.aircraft.model.entity.AircraftKor;
import info.skytag.apiserver.aircraft.repository.AircraftKorRepository;
import info.skytag.apiserver.airline.model.entity.Airline;
import info.skytag.apiserver.airline.repository.AirlineRepository;
import info.skytag.apiserver.airport.model.entity.Airport;
import info.skytag.apiserver.airport.repository.AirportRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FlightCacheService {

    private final AirlineRepository airlineRepository;
    private final AircraftKorRepository aircraftRepository;
    private final AirportRepository airportRepository;

    private Map<String, Airline> airlineCache;
    private Map<String, AircraftKor> aircraftCache;
    private Map<String, Airport> airportCache;

    @PostConstruct
    public void initializeCache() {
        loadCache();
    }

    public void loadCache() {
        airlineCache = new HashMap<>();
        aircraftCache = new HashMap<>();
        airportCache = new HashMap<>();

        List<Airline> airlines = airlineRepository.findAll();
        for (Airline airline : airlines) {
            airlineCache.put(airline.getAirlineName(), airline);
        }

        List<AircraftKor> aircrafts = aircraftRepository.findAll();
        for (AircraftKor aircraft : aircrafts) {
            aircraftCache.put(aircraft.getAircraftRegNo(), aircraft);
        }

        List<Airport> airports = airportRepository.findAll();
        for (Airport airport : airports) {
            airportCache.put(airport.getAirportName(), airport);
        }
    }

    public Airline getAirline(String airlineName) {
        return airlineCache.get(airlineName);
    }

    public AircraftKor getAircraft(String aircraftRegNo) {
        return aircraftCache.get(aircraftRegNo);
    }

    public Airport getAirport(String airportName) {
        return airportCache.get(airportName);
    }
    public Long getAirlineId(String airlineName) {
        return airlineCache.get(airlineName).getAirlineId();
    }

    public Long getAircraftId(String aircraftRegNo) {
        return aircraftCache.get(aircraftRegNo).getAircraftId();
    }

    public Long getAirportId(String airportName) {
        return airportCache.get(airportName).getAirportId();
    }

    public void putAirline(Airline airline) {
        airlineCache.put(airline.getAirlineName(), airline);
    }
    public void putAircraft(AircraftKor aircraft) {
        aircraftCache.put(aircraft.getAircraftRegNo(), aircraft);
    }
    public void putAirport(Airport airport) {
        airportCache.put(airport.getAirportName(), airport);
    }
}