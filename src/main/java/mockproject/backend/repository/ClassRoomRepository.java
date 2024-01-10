package mockproject.backend.repository;/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 23/11/2023 - 2:36 CH
 */

import mockproject.backend.domain.entity.ClassRoom;
import mockproject.backend.domain.entity.TrainingProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    @Query("select c from ClassRoom c where c.className = ?1 and c.classCode = ?2")
    Optional<ClassRoom> findByClassNameAndClassCode(String className, String classCode);
    <T> Page<T> findBy(Class<T> tClassRoom, Pageable pageable);
    Page<ClassRoom> findAll(Specification<ClassRoom> spec, Pageable pageRequest);

    List<ClassRoom> findByTrainingProgram(TrainingProgram trainingProgram);
}
