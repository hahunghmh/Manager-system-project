package mockproject.backend.repository;

import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.TrainingContent;
import mockproject.backend.domain.entity.TrainingUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingContentRepository extends JpaRepository<TrainingContent, Long> {



    List<TrainingContent> findByTrainingUnit(TrainingUnit trainingUnit);
}
