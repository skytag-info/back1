package info.skytag.apiserver.icnclient.dto.response.flight_res_detail;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FlightHeader(
        @JsonProperty("resultCode")
        String resultCode,
        @JsonProperty("resultMsg")
        String resultMsg
) {
}
