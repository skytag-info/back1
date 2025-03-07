package info.skytag.apiserver.flight.service.impl;

import info.skytag.apiserver.aircraft.model.entity.AircraftKor;
import info.skytag.apiserver.aircraft.repository.AircraftKorRepository;
import info.skytag.apiserver.airline.model.entity.Airline;
import info.skytag.apiserver.airline.repository.AirlineRepository;
import info.skytag.apiserver.airport.model.entity.Airport;
import info.skytag.apiserver.airport.repository.AirportRepository;
import info.skytag.apiserver.flight.model.dto.FlightDto;
import info.skytag.apiserver.flight.model.dto.FlightResponse;
import info.skytag.apiserver.flight.model.dto.request.FlightSearchRequest;
import info.skytag.apiserver.flight.repository.dao.ArrivalDAO;
import info.skytag.apiserver.flight.repository.dao.DepartureDAO;
import info.skytag.apiserver.flight.service.FlightService;
import info.skytag.apiserver.flight.service.cache.FlightCacheService;
import info.skytag.apiserver.icnclient.dto.response.flight_res_detail.FlightItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightCacheService flightCache;
    private final AircraftKorRepository aircraftKorRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final DepartureDAO departureDAO;
    private final ArrivalDAO arrivalDAO;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");


    @Override
    public Page<FlightResponse> searchDepartures(FlightSearchRequest request) {
        return departureDAO.searchDepartureList(request);
    }

    @Override
    public List<FlightResponse> getArrivals(FlightSearchRequest request) {
        return List.of();
    }

    @Override
    public Page<FlightResponse> searchArrivals(FlightSearchRequest request) {
        return arrivalDAO.searchArrivalList(request);
    }

    @Override
    public List<FlightResponse> getDepartures(FlightSearchRequest request) {
        return List.of();
    }



    public List<FlightDto> convertFlightToEntity(List<FlightItem> flightItems) {

        List<FlightDto> flightDtos = new ArrayList<>();

        for (FlightItem item : flightItems) {
            AircraftKor aircraft = flightCache.getAircraft(item.aircraftRegNo());
            Airline airline = flightCache.getAirline(item.airline());
            Airport airport = flightCache.getAirport(item.airport());

            FlightDto arrival = new FlightDto();

            if (airline == null && item.airline() != null && !item.airline().trim().isEmpty()) {
                airline = airlineRepository.save(new Airline(item.airline()));
                flightCache.putAirline(airline);
            }
            if (airport == null && item.airport() != null && !item.airport().trim().isEmpty()) {
                airport = airportRepository.save(new Airport(item.airport(), item.airportCode()));
                flightCache.putAirport(airport);
            }
            if (aircraft == null && item.aircraftRegNo() != null && !item.aircraftRegNo().trim().isEmpty()) {
                aircraft = aircraftKorRepository.save(new AircraftKor(item.aircraftRegNo(), airline));
                flightCache.putAircraft(aircraft);
            }


            arrival.setAircraftId(aircraft != null ? aircraft.getAircraftId() : null);
            arrival.setAirlineId(airline != null ? airline.getAirlineId() : null);
            arrival.setAirportId(airport != null ? airport.getAirportId() : null);

            arrival.setAircraftRegNo(item.aircraftRegNo());
            arrival.setAircraftSubtype(item.aircraftSubtype());
            arrival.setFid(item.fid());
            arrival.setTypeOfFlight(item.typeOfFlight());
            arrival.setFlightId(item.flightId());
            arrival.setMasterFlightId(item.masterFlightId());
            arrival.setScheduleDatetime(LocalDateTime.parse(item.scheduleDatetime(), formatter));
            arrival.setEstimatedDatetime(LocalDateTime.parse(item.estimatedDatetime(), formatter));
            arrival.setGateNumber(item.gateNumber());
            arrival.setTerminalId(item.terminalId());
            arrival.setExitNumber(item.exitNumber());
            flightDtos.add(arrival);
        }

        return flightDtos;
    }


}
