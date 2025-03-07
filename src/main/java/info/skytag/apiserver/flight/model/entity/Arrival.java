package info.skytag.apiserver.flight.model.entity;

import info.skytag.apiserver.aircraft.model.entity.AircraftKor;
import info.skytag.apiserver.airline.model.entity.Airline;
import info.skytag.apiserver.airport.model.entity.Airport;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "arrival")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Arrival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arrivalId;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private AircraftKor aircraftKor;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "airport_id")
    private Airport airport;

    private String aircraftRegNo;

    private String aircraftSubtype;

    @Column(unique = true)
    private String fid;

    private String typeOfFlight;

    private String flightId;

    private String masterFlightId;

    private LocalDateTime scheduleDatetime;

    private LocalDateTime estimatedDatetime;

    private String gateNumber;

    private String terminalId;

    private String exitNumber;

}
