package mockproject.backend.service;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.trainingprogram.TrainingForCreate;
import mockproject.backend.domain.dto.trainingprogram.TrainingForUpdate;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramForView;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramSyllabusForUpdate;
import mockproject.backend.domain.entity.ClassRoom;
import mockproject.backend.domain.entity.TrainingProgram;
import mockproject.backend.domain.entity.TrainingProgramSyllabus;
import mockproject.backend.domain.entity.enums.EStatus;
import mockproject.backend.repository.ClassRoomRepository;
import mockproject.backend.repository.SyllabusRepository;
import mockproject.backend.repository.TrainingProgramRepository;
import mockproject.backend.repository.TrainingProgramSyllabusRepository;
import mockproject.backend.service.filter.TrainingProgramSpecifications;
import mockproject.backend.service.template.TrainingProgramServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static mockproject.backend.utils.constant.ErrorCode.*;

@Service
public class TrainingProgramService implements TrainingProgramServiceInterface {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;

    @Override
    public Page<TrainingProgram> findByKeys(List<String> keys, Pageable pageRequest) {
        Specification<TrainingProgram> spec = Specification.where(null);
        if (Objects.nonNull(keys) && !keys.isEmpty()) {
            for (String key : keys) {
                spec = spec.and(TrainingProgramSpecifications.searchByKey(key));
            }
        }
        return trainingProgramRepository.findAll(spec, pageRequest);
    }

    @Override
    public Page<TrainingProgram> findByKey(String key, Pageable pageRequest) {
        Specification<TrainingProgram> spec = Specification.where(null);
        if (Objects.nonNull(key) && !key.isEmpty()) {
            spec = spec.and(TrainingProgramSpecifications.searchByKey(key));
        }
        return trainingProgramRepository.findAll(spec, pageRequest);
    }

    @Override
    public List<mockproject.backend.domain.dto.trainingprogram.TrainingProgramForView> getAll() {


        return null;
    }


    @Override
    public ResponseData create(TrainingForCreate training) {
        try {
            //Check Name đã tồn tại hay chua
            TrainingProgram existingTraining = trainingProgramRepository.findByName(training.getName());

            if (existingTraining != null) {
                return new ResponseData(-1, "TrainingProgram had exist", null);
            }

            TrainingProgram trainingProgram = modelMapper.map(training, TrainingProgram.class);
            TrainingProgram result = trainingProgramRepository.save(trainingProgram);

            // Xử lý opsionSyllabus và lưu TrainingProgramSyllabus
            // split code -> lít code

            List<String> listSyllabusCode = List.of(training.getListSyllabus().split(","));
            if (trainingProgram.getDuration() == 0) {
                result.setStatus(EStatus.DRAFTING);
            } else {
                for (int i = 0; i < listSyllabusCode.size(); i++) {
                    TrainingProgramSyllabus var3 = new TrainingProgramSyllabus();

                    var3.setTrainingProgram(result);
                    var3.setSyllabus(syllabusRepository.findByTopicCode(listSyllabusCode.get(i)));
                    var3.setSequence(i + 1);

                    trainingProgramSyllabusRepository.save(var3);
                }
            }


            return new ResponseData(0, EM61, new TrainingProgramForView(result));
        } catch (Exception e) {
            return new ResponseData(-1, "Training program can't created", null);
        }
    }


    @Override
    public List<TrainingProgramForView> findAll() {
        List<TrainingProgramForView> result = new ArrayList<>();
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.findAll();
        for (TrainingProgram trainingProgram : trainingPrograms) {
            result.add(new TrainingProgramForView(trainingProgram));
        }
        return result;
    }

    @Override
    public TrainingProgram getTrainingProgramByCode(Long trainingProgramCode) {
        return trainingProgramRepository.findByTrainingProgramCode(trainingProgramCode);
    }

    @Override
    public TrainingProgram findByTrainingProgramCode(Long trainingProgramCode) {
        return trainingProgramRepository.findByTrainingProgramCode(trainingProgramCode);
    }

    @Override
    public TrainingProgram findByTrainingCode(Long trainingProgramCode) {
        return trainingProgramRepository.findByTrainingProgramCode(trainingProgramCode);
    }


    @Override
    public TrainingProgramSyllabus saveTrainingProgramSyllabus(TrainingProgramSyllabus trainingProgramSyllabus) {
        return trainingProgramSyllabusRepository.save(trainingProgramSyllabus);
    }

    @Override
    public List<TrainingProgramSyllabusForUpdate> getSyllabusByTrainingCode(Long trainingProgramCode) {
        return trainingProgramSyllabusRepository.findByTrainingProgramTrainingProgramCode(trainingProgramCode)
                .stream()
                .map(TrainingProgramSyllabusForUpdate::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseData update(TrainingForUpdate training) {
        try {
            Optional<TrainingProgram> existingTrainingOptional = trainingProgramRepository.findById(training.getTrainingProgramCode());
            if (existingTrainingOptional.isEmpty()) {
                return new ResponseData(-1, "TrainingProgram not found", null);
            }
            TrainingProgram trainingProgram = existingTrainingOptional.get();
            trainingProgram.setStatus(training.getStatus());
            trainingProgram.setDuration(training.getDuration());

            if (trainingProgram.getStatus() == EStatus.INACTIVE || trainingProgram.getStatus() == EStatus.DRAFTING) {

                trainingProgramSyllabusRepository.deleteAll(trainingProgramSyllabusRepository.findByTrainingProgram(trainingProgram));

                List<String> listSyllabusCode = List.of(training.getListSyllabus().replaceAll("\\s", "").split(","));
                List<TrainingProgramSyllabusForUpdate> trainingProgramSyllabusDtoList = new ArrayList<>();

                for (int i = 0; i < listSyllabusCode.size(); i++) {
                    if (listSyllabusCode.get(i).trim().isEmpty()) continue;

                    TrainingProgramSyllabus trainingSyllabus = new TrainingProgramSyllabus();

                    trainingSyllabus.setTrainingProgram(trainingProgram);
                    trainingSyllabus.setSyllabus(syllabusRepository.findByTopicCode(listSyllabusCode.get(i)));
                    trainingSyllabus.setSequence(i + 1);

                    trainingProgramSyllabusRepository.save(trainingSyllabus);

                    trainingProgramSyllabusDtoList.add(new TrainingProgramSyllabusForUpdate(trainingSyllabus));
                }
                return new ResponseData(0, EM60, trainingProgramSyllabusDtoList);
            }
            return new ResponseData(-1, "Action can't perform", null);
        } catch (Exception e) {
            return new ResponseData(-1, "is not update training", null);
        }

    }

    @Override
    public ResponseData deleteTrainingProgramByCode(Long trainingProgramCode) {
        // Tìm TrainingProgram theo trainingProgramCode
//        chỉ delete status INACTIVE và DRAFTING
        TrainingProgram existingTrainingProgram = trainingProgramRepository.findByTrainingProgramCode(trainingProgramCode);
        try {
            if (existingTrainingProgram != null) {
                if (existingTrainingProgram.getStatus().equals(EStatus.INACTIVE) || existingTrainingProgram.getStatus().equals(EStatus.DRAFTING)) {
                    List<TrainingProgramSyllabus> syllabuses = trainingProgramSyllabusRepository.findByTrainingProgram(existingTrainingProgram);
                    trainingProgramSyllabusRepository.deleteAll(syllabuses);

                    List<ClassRoom> classRooms = classRoomRepository.findByTrainingProgram(existingTrainingProgram);
                    classRoomRepository.deleteAll(classRooms);

                    trainingProgramRepository.delete(existingTrainingProgram);
                    return new ResponseData(0, EM59, null);
                } else {
                    return new ResponseData(-1, "TrainingProgram is not in ACTIVE or DRAFTING", null);
                }
            } else {
                return new ResponseData(-1, "TrainingProgram Not Found", null);
            }
        } catch (Exception e) {
            return new ResponseData(-1, "TrainingProgram cannot be deleted because exists inside the class", null);
        }
    }

    @Override
    public ResponseData duplicated(Long training) {
        try {
            Optional<TrainingProgram> existingTrainingOptional = trainingProgramRepository.findById(training);

            if (existingTrainingOptional.isPresent()) {
                EStatus eStatus = existingTrainingOptional.get().getStatus();
                if (EStatus.ACTIVE.equals(eStatus)) {
                    TrainingProgram existingTrainingProgram = existingTrainingOptional.get();
                    // copy ra training mới
                    TrainingProgram duplicateTrainingProgram = new TrainingProgram();
                    duplicateTrainingProgram.setName(existingTrainingProgram.getName() + "_Copy");
                    duplicateTrainingProgram.setStatus(EStatus.INACTIVE);
                    duplicateTrainingProgram.setDuration(existingTrainingProgram.getDuration());
                    duplicateTrainingProgram = trainingProgramRepository.save(duplicateTrainingProgram);
                    // tìm các syllabus cũ và lưu qua bản training-syllabus
                    List<TrainingProgramSyllabus> existingSyllabuses = trainingProgramSyllabusRepository.findByTrainingProgram(existingTrainingProgram);
                    for (int i = 0; i < existingSyllabuses.size(); i++) {
                        TrainingProgramSyllabus existingSyllabus = existingSyllabuses.get(i);
                        TrainingProgramSyllabus newSyllabus = new TrainingProgramSyllabus();
                        newSyllabus.setTrainingProgram(duplicateTrainingProgram);
                        newSyllabus.setSyllabus(existingSyllabus.getSyllabus());
                        newSyllabus.setSequence(i + 1);
                        trainingProgramSyllabusRepository.save(newSyllabus);
                    }
                    return new ResponseData(0, EM58, new TrainingProgramForView(duplicateTrainingProgram));
                }
            }
            return new ResponseData(-1, "Training not found", null);

        } catch (Exception e) {
            return new ResponseData(-1, "Failed to duplicate training program", null);
        }
    }

    @Override
    public ResponseData findByStatus(Long trainingProgramCode) {
        Optional<TrainingProgram> optional = trainingProgramRepository.findById(trainingProgramCode);
        TrainingProgram trainingProgram = null;
        if (optional.isPresent()) {
            trainingProgram = optional.get();
            EStatus eStatus = trainingProgram.getStatus();
            if (eStatus.equals(EStatus.INACTIVE)) {
                trainingProgram.setStatus(EStatus.ACTIVE);
                trainingProgramRepository.save(trainingProgram);
                return new ResponseData(0, EM56, new TrainingProgramForView(trainingProgram));
            } else if (eStatus.equals(EStatus.ACTIVE)) {
                trainingProgram.setStatus(EStatus.INACTIVE);
            }
            trainingProgramRepository.save(trainingProgram);
            return new ResponseData(0, EM57, new TrainingProgramForView(trainingProgram));
        } else {
            return new ResponseData(-1, EM55, null);
        }
    }
}
