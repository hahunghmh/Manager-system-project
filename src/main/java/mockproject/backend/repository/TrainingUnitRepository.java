package mockproject.backend.repository;

import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.TrainingUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingUnitRepository extends JpaRepository<TrainingUnit, Long> {
    void deleteBySyllabus_TopicCode(String topicCode);

    List<TrainingUnit> findBySyllabus(Syllabus syllabus);
}
