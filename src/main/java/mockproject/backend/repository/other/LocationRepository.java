package mockproject.backend.repository.other;

import mockproject.backend.domain.entity.other.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query("select l.code from Location l ")
    List<String> getAllCode();

}