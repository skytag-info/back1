package info.skytag.apiserver.icnclient.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.skytag.apiserver.icnclient.dto.response.flight_res_detail.FlightResponse;

public record FlightResDto(
        @JsonProperty("response")
        FlightResponse response
) {
}