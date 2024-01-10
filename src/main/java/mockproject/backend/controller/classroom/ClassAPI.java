package mockproject.backend.controller.classroom;
/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 23/11/2023 - 2:43 CH
 */


import mockproject.backend.domain.dto.PageConfigClassFilter;
import mockproject.backend.domain.dto.PageConfigVer2;
import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.classroom.ClassRoomChangeTrainingProgram;
import mockproject.backend.domain.dto.classroom.ClassRoomForCreateView;
import mockproject.backend.domain.dto.classroom.ClassRoomForView;
import mockproject.backend.domain.dto.classroom.ClassRoomUpdateSyllabus;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramCode;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramForClassCreate;
import mockproject.backend.domain.entity.TrainingProgram;
import mockproject.backend.service.ClassRoomService;
import mockproject.backend.service.TrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static mockproject.backend.utils.constant.ClassUrlAPI.*;
import static mockproject.backend.utils.constant.ErrorCode.EM00;


@RestController
public class ClassAPI {

    @Autowired
    private ClassRoomService classService;

    @Autowired
    private TrainingProgramService trainingProgramService;

    @PostMapping(CLASS_CREATE)
    public ResponseEntity<Object> createClass(@RequestBody ClassRoomForCreateView createClass) {
        classService.createClass(createClass);
        return ResponseEntity.ok(new ResponseData(0, EM00, null));
    }

    @PostMapping(CLASS_FILTER)
    public ResponseEntity<Object> getClassManagerWithFilter(@RequestBody PageConfigClassFilter config) {
        ResponseData resultRes = classService.advancedFilter(config);
        return ResponseEntity.ok(resultRes);
    }

    @PostMapping(CLASS_VIEW)
    public ResponseEntity<ResponseData> getClassManager(@RequestBody PageConfigVer2 config) {

        String keyword = null;
        Page<ClassRoomForView> result = classService.findByKey(keyword, config.getPageableConfig()).map(ClassRoomForView::new);
        ResponseData resultRes = new ResponseData(0, EM00, result);
        return ResponseEntity.ok(resultRes);
    }

    //get info : user, fsu, location , training program for create class
    @GetMapping(CLASS_GET_ALL)
    public ResponseEntity<ResponseData> getClassInfo() {
        ResponseData resultRes = classService.getClassInfo();
        return ResponseEntity.ok(resultRes);
    }

    @GetMapping(CLASS_GET_LOCATION)
    public ResponseEntity<Object> getLocation() {
        return ResponseEntity.ok(classService.getLocation());
    }

    @GetMapping(CLASS_GET_DETAIL + "/{name}/{code}")
    public ResponseEntity<ResponseData> getClassDetail(@PathVariable("name") String name, @PathVariable("code") String code) {
        ResponseData resultRes = classService.getClassDetailByCode(name,code);
        return ResponseEntity.ok(resultRes);
    }


    @PostMapping(CLASS_GET_DETAIL)
    public ResponseEntity<ResponseData> getTrainingDetails(@RequestBody TrainingProgramCode trainingProgramCode) {
        ResponseData resultRes = null;

        if (trainingProgramCode != null && trainingProgramCode.getTrainingProgramCode() != null) {
            TrainingProgram trainingProgram = trainingProgramService.getTrainingProgramByCode(trainingProgramCode.getTrainingProgramCode());
            if (trainingProgram != null) {
                resultRes = new ResponseData(0, EM00, new TrainingProgramForClassCreate(trainingProgram));
            }
        }
        return ResponseEntity.ok(resultRes);
    }

    @PostMapping(CLASS_CHANGE_TRAINING)
    public ResponseEntity<ResponseData> changeTrainingProgram(@RequestBody ClassRoomChangeTrainingProgram form) {
        if (form == null) return ResponseEntity.badRequest().body(new ResponseData(0, "update fail", null));
        return ResponseEntity.ok().body(classService.changeTrainingProgramForClass(form));
    }

    @PostMapping(CLASS_CHANGE_SYLLABUS)
    public ResponseEntity<ResponseData> changeSyllabus(@RequestBody ClassRoomUpdateSyllabus form) {
        if (form == null) return ResponseEntity.badRequest().body(new ResponseData(0, "update fail", null));
        return ResponseEntity.ok().body(classService.deleteOrUpdateSyllabus(form));
    }
}
