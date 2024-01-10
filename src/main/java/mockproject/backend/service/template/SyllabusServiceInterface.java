package mockproject.backend.service.template;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.syllabus.SyllabusForCreate;
import mockproject.backend.domain.dto.syllabus.SyllabusForView;
import mockproject.backend.domain.dto.syllabus.SyllabusForUpdate;
import mockproject.backend.domain.entity.Syllabus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SyllabusServiceInterface {


    Page<Syllabus> filterByKeyword(String dateRange, List<String> keys, Pageable pageable);

    List<SyllabusForView> findAllByTopicName(String topicName);

    List<SyllabusForView> findAll();

    List<SyllabusForView> findAllActive();

    Syllabus findByTopicCode(String topicCode);

    ResponseData create(SyllabusForCreate syllabus);

    ResponseData update(String topicCode, SyllabusForUpdate syllabus);

    // Method to duplicate
    ResponseData duplicate(String topicCode);
}
