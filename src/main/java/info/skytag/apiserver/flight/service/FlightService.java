package info.skytag.apiserver.flight.service;

import info.skytag.apiserver.flight.model.dto.FlightDto;
import info.skytag.apiserver.flight.model.dto.FlightResponse;
import info.skytag.apiserver.flight.model.dto.request.FlightSearchRequest;
import info.skytag.apiserver.icnclient.dto.response.flight_res_detail.FlightItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FlightService {
    Page<FlightResponse> searchDepartures(FlightSearchRequest request);
    List<FlightResponse> getDepartures(FlightSearchRequest request);
    Page<FlightResponse> searchArrivals(FlightSearchRequest request);
    List<FlightResponse> getArrivals(FlightSearchRequest request);
    List<FlightDto> convertFlightToEntity(List<FlightItem> flightItems);
}
