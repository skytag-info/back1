package info.skytag.apiserver.flight.controller;

import info.skytag.apiserver.flight.model.dto.FlightResponse;
import info.skytag.apiserver.flight.model.dto.request.FlightSearchRequest;
import info.skytag.apiserver.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightRestController {
    private final FlightService flightService;

    @GetMapping("/departure")
    public Page<FlightResponse> getDepartures(FlightSearchRequest request) {
        return flightService.searchDepartures(request);
    }

    @GetMapping("/arrivals")
    public Page<FlightResponse> getArrivals(FlightSearchRequest request) {
        return flightService.searchArrivals(request);
    }
}
