package info.skytag.apiserver.icnclient.dto.response.flight_res_detail;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FlightResponse(
        @JsonProperty("header")
        FlightHeader header,

        @JsonProperty("body")
        FlightBody body
) {
}
