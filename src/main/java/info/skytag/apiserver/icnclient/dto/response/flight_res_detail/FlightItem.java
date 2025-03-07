package info.skytag.apiserver.icnclient.dto.response.flight_res_detail;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FlightItem(

        @JsonProperty("fid")
        String fid,

        @JsonProperty("codeshare")
        String codeshare,

        @JsonProperty("masterFlightId")
        String masterFlightId,

        @JsonProperty("flightId")
        String flightId,

        @JsonProperty("airline")
        String airline,

        @JsonProperty("airport")
        String airport,

        @JsonProperty("airportCode")
        String airportCode,

        @JsonProperty("passengerOrCargo")
        String passengerOrCargo,

        @JsonProperty("scheduleDatetime")
        String scheduleDatetime,

        @JsonProperty("estimatedDatetime")
        String estimatedDatetime,

        @JsonProperty("carousel")
        String carousel,

        @JsonProperty("exitNumber")
        String exitNumber,

        @JsonProperty("fstandPosition")
        String fstandPosition,

        @JsonProperty("gateNumber")
        String gateNumber,

        @JsonProperty("terminalId")
        String terminalId,

        @JsonProperty("remark")
        String remark,

        @JsonProperty("typeOfFlight")
        String typeOfFlight,

        @JsonProperty("aircraftSubtype")
        String aircraftSubtype,

        @JsonProperty("aircraftRegNo")
        String aircraftRegNo,

        @JsonProperty("tmp1")
        String tmp1,

        @JsonProperty("tmp2")
        String tmp2
) {}