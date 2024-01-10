package mockproject.backend.repository;


import mockproject.backend.domain.entity.ClassRoom;
import mockproject.backend.domain.entity.TrainingProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {
    long countByTrainingProgramCode(Long trainingProgramCode);

    TrainingProgram findByTrainingProgramCode(Long trainingProgramCode);

    @Query("SELECT c FROM ClassRoom c ORDER BY c.fsu ASC")
    List<ClassRoom> findAllFsu();

    Page<TrainingProgram> findAll(Specification<TrainingProgram> spec, Pageable pageRequest);

    TrainingProgram findByName(String name);

    List<TrainingProgram> findAll();
 }
