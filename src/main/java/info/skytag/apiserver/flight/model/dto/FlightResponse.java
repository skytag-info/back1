package info.skytag.apiserver.flight.model.dto;

import com.querydsl.core.types.OrderSpecifier;
import info.skytag.apiserver.aircraft.model.dto.response.AircraftResponse;
import info.skytag.apiserver.airline.model.dto.response.AirlineResponse;
import info.skytag.apiserver.airport.model.dto.response.AirportResponse;
import lombok.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    private AirportResponse airport;
    private AirlineResponse airline;
    private AircraftResponse aircraft;
    private String typeOfFlight;
    private String flightId;
    private String masterFlightId;
    private LocalDateTime scheduleDatetime;
    private String gateNumber;
    private String terminalId;
    private String exitNumber;
}
