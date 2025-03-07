package info.skytag.apiserver.airline.repository;

import info.skytag.apiserver.airline.model.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
