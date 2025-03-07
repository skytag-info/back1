package info.skytag.apiserver.aircraft.model.entity;

import info.skytag.apiserver.airline.model.entity.Airline;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AircraftKor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aircraftId;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

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

    public AircraftKor(String aircraftName, Airline airline){
        this.aircraftRegNo = aircraftName;
        this.airline = airline;
    }
}
