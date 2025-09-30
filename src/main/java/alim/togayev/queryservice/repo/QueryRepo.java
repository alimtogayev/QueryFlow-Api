package alim.togayev.queryservice.repo;

import alim.togayev.queryservice.entities.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepo extends JpaRepository<Query, Long> {
}
