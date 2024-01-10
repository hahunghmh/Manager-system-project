/*
 *@project : FrontEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/22/2023 - 9:19 AM
 */


package mockproject.backend.controller.training;


import mockproject.backend.domain.dto.PageConfig;
import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.trainingprogram.TrainingForCreate;
import mockproject.backend.domain.dto.trainingprogram.TrainingForUpdate;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramForView;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramSyllabusForUpdate;
import mockproject.backend.domain.entity.TrainingProgram;
import mockproject.backend.service.TrainingProgramService;
import mockproject.backend.utils.validation.ValidationInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.EM00;
import static mockproject.backend.utils.constant.TrainingUrlAPI.*;

@RestController
@RequestMapping
public class TrainingAPI {
    //    API for training
    @Autowired
    private TrainingProgramService trainingProgramService;

    private ValidationInterface validationInterface;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(TRAINING_PROGRAM_VIEW)
    public ResponseEntity<ResponseData> getUserManager(@RequestBody PageConfig config) {

        String keyword = config.getKeyword();
        Page<TrainingProgramForView> result = trainingProgramService.findByKey(keyword, config.getPageableConfig()).map(TrainingProgramForView::new);

        ResponseData resultRes = new ResponseData(0, EM00, result);
        return ResponseEntity.ok(resultRes);
    }

    @PostMapping(TRAINING_PROGRAM_CREATE)
    public ResponseEntity<Object> createTraining(@Valid @RequestBody TrainingForCreate training, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // return error
            return ResponseEntity.ok().body(validationInterface.error(bindingResult));
        }

        ResponseData result = trainingProgramService.create(training);
        return ResponseEntity.ok(result);
    }

    @GetMapping(TRAINING_PROGRAM_ALL_LIST)
    public ResponseEntity<Object> getAllList() {
        ResponseData responseData = new ResponseData(0, EM00, trainingProgramService.findAll());
        return ResponseEntity.ok(responseData);
    }


    @PostMapping(TRAINING_PROGRAM_BY_TRAININGCODE_SYLLABUS)
    public ResponseEntity<Object> getSyllabusByTrainingCode(@RequestParam Long trainingProgramCode) {
        List<TrainingProgramSyllabusForUpdate> syllabusList = trainingProgramService.getSyllabusByTrainingCode(trainingProgramCode);
        ResponseData responseData = new ResponseData(0, EM00, syllabusList);
        return ResponseEntity.ok((responseData));
    }

    @PutMapping(TRAINING_PROGRAM_UPDATE)
    public ResponseEntity<Object> updateTrainingProgram(@Valid @RequestBody TrainingForUpdate trainingForUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // return error
            return ResponseEntity.ok().body(validationInterface.error(bindingResult));
        }
        return ResponseEntity.ok(trainingProgramService.update(trainingForUpdate));
    }

    @DeleteMapping(TRAINING_PROGRAM_DELETE)
    public ResponseEntity<Object> deleteTrainingProgram(@PathVariable Long trainingProgramCode) {
        try {
            // Gọi phương thức delete từ service
            ResponseData responseData=trainingProgramService.deleteTrainingProgramByCode(trainingProgramCode);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(-1, "Lỗi xóa training program", null));
        }
    }

    @GetMapping(TRAINING_PROGRAM_DUPLICATE)
    public ResponseEntity<Object> duplicate(@PathVariable("trainingProgramCode") Long trainingProgramCode) {
        ResponseData responseData = trainingProgramService.duplicated(trainingProgramCode);
        if (responseData.getStatusCode() != 0) {
            return ResponseEntity.badRequest().body(responseData);
        }
        return ResponseEntity.ok(responseData);
    }


    @GetMapping(TRAINING_PROGRAM_BY_TRAININGCODE)
    public ResponseEntity<Object> getByTrainingCode(@PathVariable("trainingProgramCode") Long training) {
        TrainingProgram training1 = trainingProgramService.findByTrainingCode(training);
        TrainingForUpdate training2 = new TrainingForUpdate(training1);


        ResponseData responseData = new ResponseData(0, EM00, training2);
        return ResponseEntity.ok((responseData));
    }
    @GetMapping(TRAINING_PROGRAM_STATUS)
    public ResponseEntity<Object> getStatus(@PathVariable("trainingProgramCode") Long trainingCode){
        ResponseData responseData =trainingProgramService.findByStatus(trainingCode);
        return ResponseEntity.ok(responseData);
    }
}
