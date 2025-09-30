package alim.togayev.queryservice.repo;

import alim.togayev.queryservice.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepo extends JpaRepository<Passenger, Long> {
}
