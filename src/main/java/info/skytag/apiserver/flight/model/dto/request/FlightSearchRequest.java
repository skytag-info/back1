package info.skytag.apiserver.flight.model.dto.request;

import info.skytag.apiserver.common.dto.request.SortCriteria;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class FlightSearchRequest {
    private String airportCode;
    private String airportName;
    private String airportCity;
    private String airportCountry;
    private String airlineName;
    private String iataCode;
    private String icaoCode;
    private String airlineCountry;
    private String airlineContactInfo;
    private String aircraftRegNo;

    private String businessType;
    private String aircraftType;
    private String aircraftSerialNumber;
    private LocalDateTime manufacturingStartDate;
    private LocalDateTime manufacturingEndDate;
    private Integer aircraftAge;
    private LocalDateTime registrationStartDate;
    private LocalDateTime registrationEndDate;
    private Integer minSeatingCapacity;
    private Integer maxSeatingCapacity;
    private String maximumTakeoffWeight;

    private String typeOfFlight;
    private String flightId;
    private String masterFlightId;
    private LocalDateTime scheduleStartDatetime;
    private LocalDateTime scheduleEndDatetime;
    private String gateNumber;
    private String terminalId;
    private String exitNumber;

    private int page = 0;
    private int size = 10;
    private List<SortCriteria> sorts = new ArrayList<>();

    public Pageable toPageable() {
        if (sorts.isEmpty()) {
            return PageRequest.of(page, size);
        }

        List<Sort.Order> orders = sorts.stream()
                .map(sort -> new Sort.Order(
                        "ASC".equalsIgnoreCase(sort.getDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                        sort.getField()))
                .collect(Collectors.toList());

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
