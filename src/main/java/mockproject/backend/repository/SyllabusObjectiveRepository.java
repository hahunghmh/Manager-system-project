package mockproject.backend.repository;

import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.SyllabusObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyllabusObjectiveRepository extends JpaRepository<SyllabusObject, Long> {
    void deleteBySyllabus_TopicCode(String topicCode);

    List<SyllabusObject> findBySyllabus(Syllabus syllabus);
}
