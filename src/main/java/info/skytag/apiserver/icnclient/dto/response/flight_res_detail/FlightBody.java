package info.skytag.apiserver.icnclient.dto.response.flight_res_detail;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FlightBody(
        @JsonProperty("numOfRows")
        int numOfRows,
        @JsonProperty("pageNo")
        int pageNo,
        @JsonProperty("totalCount")
        int totalCount,
        @JsonProperty("items")
        List<FlightItem> items
) {}