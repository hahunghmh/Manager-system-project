package mockproject.backend.repository;

import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.enums.EStatus;
import mockproject.backend.domain.entity.enums.EUserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SyllabusRepository extends JpaRepository<Syllabus, String> {
    List<Syllabus> findByPublicStatus(EStatus publicStatus);
    Page<Syllabus> findAll(Specification<Syllabus> spec, Pageable pageRequest);


    //    @Query("from Syllabus u where LOWER(u.topicName) LIKE LOWER(CONCAT('%', :searchString, '%'))")
    List<Syllabus> findAllByTopicNameIsContaining(String topicName);

    Syllabus findByTopicCode(String topicCode);

    @Query(value="SELECT s.topic_Code FROM Syllabus s ORDER BY s.topic_Code DESC LIMIT 1", nativeQuery=true)
    String findLatestTopicCode();

}
