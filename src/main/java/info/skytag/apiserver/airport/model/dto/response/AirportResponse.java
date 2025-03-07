package info.skytag.apiserver.airport.model.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {
    private String airportCode;

    private String airportName;

    private String city;

    private String country;

    private String type;}
