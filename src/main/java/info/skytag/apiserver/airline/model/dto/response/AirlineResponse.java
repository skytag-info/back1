package info.skytag.apiserver.airline.model.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineResponse {
    private String airlineName;

    private String iataCode;

    private String icaoCode;

    private String country;

    private String contactInfo;

    private String websiteUrl;
}
