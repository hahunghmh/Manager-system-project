package mockproject.backend.repository;


import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.TrainingProgram;
import mockproject.backend.domain.entity.TrainingProgramSyllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingProgramSyllabusRepository extends JpaRepository<TrainingProgramSyllabus, Long> {

    List<TrainingProgramSyllabus> findByTrainingProgramTrainingProgramCode(Long trainingCode);

    @Query("select t.syllabus.topicCode from TrainingProgramSyllabus t where t.trainingProgram.trainingProgramCode =:id")
    List<String> getSyllabusById(@Param("id") long id);
    List<TrainingProgramSyllabus> findByTrainingProgram(TrainingProgram trainingProgram);

    List<TrainingProgramSyllabus> findBySyllabus(Syllabus oldSyllabus);
}