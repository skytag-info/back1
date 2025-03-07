package info.skytag.apiserver.airport.repository;

import info.skytag.apiserver.airport.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
