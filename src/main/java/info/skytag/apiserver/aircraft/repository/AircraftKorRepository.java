package info.skytag.apiserver.aircraft.repository;


import info.skytag.apiserver.aircraft.model.entity.AircraftKor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftKorRepository extends JpaRepository<AircraftKor, Long> {
}
