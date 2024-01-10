package mockproject.backend.controller.syllabus;

import mockproject.backend.domain.dto.PageConfig;
import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.syllabus.SyllabusForCreate;
import mockproject.backend.domain.dto.syllabus.SyllabusForUpdate;
import mockproject.backend.domain.dto.syllabus.SyllabusForView;
import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.repository.LearningObjectiveRepository;
import mockproject.backend.service.SyllabusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.EM00;
import static mockproject.backend.utils.constant.SyllabusUrlAPI.*;


@RestController
@RequestMapping
public class SyllabusAPI {
    @Autowired
    private SyllabusService syllabusService;
    @Autowired
    private LearningObjectiveRepository learningObjectiveRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(SYLLABUS_VIEW)
    public ResponseEntity<ResponseData> getSyllabusViews(@RequestBody PageConfig config) {
        String dateRange = "";
        List<String> stringList = new ArrayList<>();
        String keyword = config.getKeyword();

        String[] data = keyword.split(",");
        if (data.length > 0) {
            dateRange = data[0].trim();
            // Extract the list of strings
            for (int i = 1; i < data.length; i++) {
                stringList.add(data[i].trim());
            }
        }
        Page<SyllabusForView> result = syllabusService.filterByKeyword(dateRange, stringList, config.getPageableConfig()).map(SyllabusForView::new);

        result.getContent().forEach(syllabusForView -> {
            List<String> outputStandard = learningObjectiveRepository.findDistinctByTrainingContent_TrainingUnit_Syllabus_TopicCode(syllabusForView.getTopicCode());
            syllabusForView.setOutputStandard(outputStandard);

        });


        ResponseData resultRes = new ResponseData(0, EM00, result);
        return ResponseEntity.ok(resultRes);
    }

    @GetMapping(SYLLABUS_ALL_LIST)
    public ResponseEntity<Object> getAllList() {
        ResponseData responseData = new ResponseData(0, EM00, syllabusService.findAllActive());
        return ResponseEntity.ok(responseData);
    }


    @PostMapping(SYLLABUS_BY_TOPICCODE)
    public ResponseEntity<Object> topicCode(@RequestBody SyllabusForView syllarbusTopic) {
        List<SyllabusForView> result = new ArrayList<>();
        if (syllarbusTopic != null && syllarbusTopic.getTopicCode() != null) {
            String byTopicCode = syllarbusTopic.getTopicCode();
            Syllabus syllabus = syllabusService.findByTopicCode(byTopicCode);
            if (syllabus != null) {
                // Chuyển đổi từ TrainingProgram sang TrainingProgramView và thêm vào danh sách kết
                result.add(new SyllabusForView(syllabus));
            }
        }
        ResponseData responseData = new ResponseData(0, EM00, result);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping(SYLLABUS_CREATE)
    public ResponseEntity<Object> createSyllabus(@Valid @RequestBody SyllabusForCreate syllabus, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        ResponseData result = syllabusService.create(syllabus);
        return ResponseEntity.ok(result);
    }

    @PostMapping(SYLLABUS_UPDATE)
    public ResponseEntity<Object> updateSyllabus(
            @Valid @RequestBody SyllabusForUpdate syllabusForUpdate, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        String id = syllabusForUpdate.getTopicCode();

        // Check if Syllabus with the given topicCode exists
        Syllabus existingSyllabus = syllabusService.findByTopicCode(id);
        if (existingSyllabus == null) {
            return ResponseEntity.notFound().build();  // Return 404 if not found
        }
        // Perform the update using the SyllabusService
        ResponseData result = syllabusService.update(id, syllabusForUpdate);
        return ResponseEntity.ok(result);
    }

    @GetMapping(SYLLABUS_GET_UPDATE)
    public ResponseEntity<Object> getSyllabusUpdate(@PathVariable("id") String id) {
        Syllabus syllabus = syllabusService.findByTopicCode(id);
        if (syllabus == null) {
            return ResponseEntity.notFound().build();
        }

        // Lấy danh sách outputStandard tương ứng với topicCode
        List<String> outputStandard = learningObjectiveRepository.findDistinctByTrainingContent_TrainingUnit_Syllabus_TopicCode(id);

        SyllabusForUpdate syllabusForUpdate = new SyllabusForUpdate(syllabus);
        syllabusForUpdate.setOutputStandard(outputStandard);

        return ResponseEntity.ok(new ResponseData(0, EM00, syllabusForUpdate));
    }

    @GetMapping(SYLLABUS_DUPLICATE)
    public ResponseEntity<Object> duplicateSyllabus(@PathVariable("topicCode") String topicCode) {
        ResponseData result = syllabusService.duplicate(topicCode);
        return ResponseEntity.ok(result);
    }

    @GetMapping(SYLLABUS_DELETE)
    public ResponseEntity<Object> deleteSyllabus(@PathVariable("topicCode") String topicCode) {
        ResponseData result = syllabusService.delete(topicCode);
        return ResponseEntity.ok(result);
    }

}
