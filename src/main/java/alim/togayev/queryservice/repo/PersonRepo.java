package alim.togayev.queryservice.repo;

import alim.togayev.queryservice.entities.Person;
import alim.togayev.queryservice.entities.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Long> {
}
