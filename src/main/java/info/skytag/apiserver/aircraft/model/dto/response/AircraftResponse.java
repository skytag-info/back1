package info.skytag.apiserver.aircraft.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftResponse {
    private String aircraftRegNo;

    private String businessType;

    private String aircraftType;

    private String serialNumber;

    private LocalDateTime manufacturingDate;

    private Integer age;

    private LocalDateTime registrationDate;

    private Integer seatingCapacity;

    private String maximumTakeoffWeight;

    private String parkingArea;

    private String acquisitionType;
}
