package info.skytag.apiserver.flight.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightDto {

    private Long aircraftId;
    private Long airlineId;
    private Long airportId;
    private String aircraftRegNo;
    private String aircraftSubtype;
    private String fid;
    private String typeOfFlight;
    private String flightId;
    private String masterFlightId;
    private LocalDateTime scheduleDatetime;
    private LocalDateTime estimatedDatetime;
    private String gateNumber;
    private String terminalId;
    private String exitNumber;
}
