package info.skytag.apiserver.airline.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "airline")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id")
    private Long airlineId;

    private String airlineName;

    private String iataCode;

    private String icaoCode;

    private String country;

    private String contactInfo;

    private String websiteUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public Airline(String airlineName){
        this.airlineName = airlineName;
    }
}