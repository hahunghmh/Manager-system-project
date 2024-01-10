package mockproject.backend.service.template;


import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.trainingprogram.TrainingForCreate;
import mockproject.backend.domain.dto.trainingprogram.TrainingForUpdate;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramForView;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramSyllabusForUpdate;
import mockproject.backend.domain.entity.TrainingProgram;
import mockproject.backend.domain.entity.TrainingProgramSyllabus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TrainingProgramServiceInterface {
    Page<TrainingProgram> findByKeys(List<String> keys, Pageable pageRequest);

    Page<TrainingProgram> findByKey(String key, Pageable pageRequest);

    List<mockproject.backend.domain.dto.trainingprogram.TrainingProgramForView> getAll();

    ResponseData create(TrainingForCreate training);

    List<TrainingProgramForView> findAll();

    TrainingProgram getTrainingProgramByCode(Long trainingProgramCode);

    TrainingProgram findByTrainingProgramCode(Long trainingProgramCode);

    TrainingProgram findByTrainingCode(Long trainingProgramCode);

    TrainingProgramSyllabus saveTrainingProgramSyllabus(TrainingProgramSyllabus trainingProgramSyllabus);

    List<TrainingProgramSyllabusForUpdate> getSyllabusByTrainingCode(Long trainingProgramCode);

    ResponseData update(TrainingForUpdate training);

    ResponseData deleteTrainingProgramByCode(Long trainingProgramCode);

    ResponseData duplicated(Long training);

    ResponseData findByStatus(Long trainingProgramCode);
}
