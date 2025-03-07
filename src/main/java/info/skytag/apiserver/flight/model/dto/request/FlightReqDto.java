package info.skytag.apiserver.flight.model.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class FlightReqDto {
    private String pageNo;
    private String numOfRows;
    private String searchdtCode;
    private String searchDate;
    private String searchFrom;
    private String searchTo;
    private String passengerOrCargo;
    private String fid;
    private String flightId;
    private String airportCode;
    private String tmp1;
    private String tmp2;

    public FlightReqDto(int pageNo, int numOfRows, LocalDate searchDate) {
        this.pageNo = String.valueOf(pageNo);
        this.numOfRows = String.valueOf(numOfRows);
        this.searchDate = formatLocalDate(searchDate);

    }

    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}