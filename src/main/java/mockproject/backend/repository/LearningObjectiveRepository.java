package mockproject.backend.repository;/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 03/12/2023 - 3:57 CH
 */

import mockproject.backend.domain.entity.LearningObjective;
import mockproject.backend.domain.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LearningObjectiveRepository extends JpaRepository<LearningObjective, Long> {
    LearningObjective findByCode(Long code);


    List<LearningObjective> findByCode(Syllabus syllabus);
    @Query(value = "SELECT DISTINCT l.type " +
            "FROM learning_objective l " +
            "JOIN training_content tc ON l.traning_content_id = tc.traning_content_id " +
            "JOIN training_unit tu ON tc.training_unit_id = tu.training_unit_id " +
            "JOIN syllabus s ON tu.topic_code = s.topic_code " +
            "WHERE s.topic_code = ?1 " +
            "LIMIT 3", nativeQuery = true)
    List<String> findDistinctByTrainingContent_TrainingUnit_Syllabus_TopicCode(String topicCode);

}
