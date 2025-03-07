package info.skytag.apiserver.airport.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "airport")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Long airportId;

    private String airportCode;

    private String airportName;

    private String city;

    private String country;

    private String type;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public Airport(String airportName, String airportCode){
        this.airportName = airportName;
        this.airportCode = airportCode;
    }
}