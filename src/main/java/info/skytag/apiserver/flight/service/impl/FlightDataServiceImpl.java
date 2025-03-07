package info.skytag.apiserver.flight.service.impl;

import info.skytag.apiserver.flight.model.dto.FlightDto;
import info.skytag.apiserver.flight.repository.mapper.ArrivalMapper;
import info.skytag.apiserver.flight.service.FlightDataService;
import info.skytag.apiserver.icnclient.dto.response.flight_res_detail.FlightItem;
import info.skytag.apiserver.icnclient.service.impl.ArrivalIcnFlightServiceImpl;
import info.skytag.apiserver.icnclient.service.impl.DepartureIcnFlightServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightDataServiceImpl implements FlightDataService {

    private final ArrivalIcnFlightServiceImpl arrivalService;
    private final DepartureIcnFlightServiceImpl departureService;
    private final FlightServiceImpl convertService;
    private final ArrivalMapper arrivalMapper;

    @Override
    public long getTotalDepartureSize(LocalDate searchDate) {
        return arrivalService.getTotalNum(searchDate);
    }

    @Override
    public List<FlightDto> getDepartureListByDate(int pageNo, int numOfRows, LocalDate searchDate) {

        List<FlightItem> flightItems = arrivalService.getFlightListByDate(pageNo, numOfRows , searchDate);
        List<FlightDto> flightDtos = convertService.convertFlightToEntity(flightItems);

        return flightDtos;
    }

    @Override
    public long getTotalArrivalSize(LocalDate searchDate) {
        return departureService.getTotalNum(searchDate);
    }

    @Override
    public List<FlightDto> getArrivalListByDate(int pageNo, int numOfRows, LocalDate searchDate) {
        List<FlightItem> flightItems = arrivalService.getFlightListByDate(pageNo, numOfRows , searchDate);
        List<FlightDto> flightDtos = convertService.convertFlightToEntity(flightItems);

        return flightDtos;
    }

    @Override
    public void fetchAllArrivals(LocalDate searchDate) {

        int total = arrivalService.getTotalNum(searchDate);
        int cnt = (total % 100 == 0) ? (total / 100) : (total / 100 + 1);

        for (int i = 0; i < cnt; i++) {
            List<FlightItem> flightItems = arrivalService.getFlightListByDate(i+1, 100 , searchDate);
            List<FlightDto> flightDtos = convertService.convertFlightToEntity(flightItems);

            arrivalMapper.bulkInsertArrival(flightDtos);
        }
    }

    @Override
    public void fetchAllDepartures(LocalDate searchDate) {

        int total = departureService.getTotalNum(searchDate);
        int cnt = (total % 100 == 0) ? (total / 100) : (total / 100 + 1);

        for (int i = 0; i < cnt; i++) {

            List<FlightItem> flightItems = departureService.getFlightListByDate(i+1, 100 , searchDate);
            List<FlightDto> flightDtos = convertService.convertFlightToEntity(flightItems);

            arrivalMapper.bulkInsertDeparture(flightDtos);
        }
    }

    @Override
    public void saveAllDepartures(List<FlightDto> departures) {
        arrivalMapper.bulkInsertDeparture(departures);
    }

    @Override
    public void saveAllArrivals(List<FlightDto> arrivals) {
        arrivalMapper.bulkInsertArrival(arrivals);
    }
}
