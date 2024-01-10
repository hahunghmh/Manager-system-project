package mockproject.backend.service.template;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.classroom.ClassRoomChangeTrainingProgram;
import mockproject.backend.domain.dto.classroom.ClassRoomForCreateView;
import mockproject.backend.domain.dto.classroom.ClassRoomForView;
import mockproject.backend.domain.dto.classroom.ClassRoomUpdateSyllabus;
import mockproject.backend.domain.entity.ClassRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClassRoomServiceInterface {
    ClassRoom getById(Long id);

    ResponseData deleteOrUpdateSyllabus(ClassRoomUpdateSyllabus form);

    ResponseData changeTrainingProgramForClass(ClassRoomChangeTrainingProgram form);

    Page<ClassRoom> findByKey(String key, Pageable pageRequest);

    //    for create class
    ResponseData getClassInfo();

    List<ClassRoomForView> getFSU();

    ResponseData createClass(ClassRoomForCreateView createClass);

    ResponseData getClassDetailByCode(String name, String code);
}
