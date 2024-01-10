package mockproject.backend.service;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.syllabus.SyllabusForCreate;
import mockproject.backend.domain.dto.syllabus.SyllabusForUpdate;
import mockproject.backend.domain.dto.syllabus.SyllabusForView;
import mockproject.backend.domain.entity.*;
import mockproject.backend.domain.entity.enums.EStatus;
import mockproject.backend.domain.entity.enums.EUserStatus;
import mockproject.backend.repository.*;
import mockproject.backend.service.filter.SyllabusSpecifications;
import mockproject.backend.service.template.SyllabusServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static mockproject.backend.utils.constant.ErrorCode.EM00;

@Service
public class SyllabusService implements SyllabusServiceInterface {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SyllabusRepository syllabusRepository;

    @Autowired
    private TrainingUnitRepository trainingUnitRepository;

    @Autowired
    private TrainingContentRepository trainingContentRepository;

    @Autowired
    private LearningObjectiveRepository learningObjectiveRepository;

    @Autowired
    private SyllabusObjectiveRepository syllabusObjectiveRepository;

    @Autowired
    private TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;

    @Override
    public Page<Syllabus> filterByKeyword(String dateRange, List<String> keys, Pageable pageable) {

        Specification<Syllabus> spec = Specification.where(null);

//        filter by multi keys
        if (Objects.nonNull(keys) && !keys.isEmpty()) {
            for (String key : keys) {
                spec = spec.and(SyllabusSpecifications.searchByKey(key));
            }
        }
        if (Objects.nonNull(dateRange) && !dateRange.isEmpty()) {
            String[] dateParts = dateRange.split(" to ");
            if (dateParts.length == 2) {
                LocalDate startDate = LocalDate.parse(dateParts[0]);
                LocalDate endDate = LocalDate.parse(dateParts[1]);
                spec = spec.and(SyllabusSpecifications.filterByCreatedDate(startDate, endDate));

            } else if (dateParts.length == 1) {
                LocalDate startDate = LocalDate.parse(dateParts[0]);
                spec = spec.and(SyllabusSpecifications.filterByCreatedDate(startDate, null));

            }
        }
        return syllabusRepository.findAll(spec, pageable);
    }


    //    for filter by date
    @Override
    public List<SyllabusForView> findAllByTopicName(String topicName) {

        List<SyllabusForView> result = new ArrayList<>();
        for (Syllabus syllabus : syllabusRepository.findAllByTopicNameIsContaining(topicName)) {
            result.add(modelMapper.map(syllabus, SyllabusForView.class));
        }

        return result;
    }

    @Override
    public List<SyllabusForView> findAll() {
        List<SyllabusForView> result = new ArrayList<>();
        for (Syllabus syllabus : syllabusRepository.findAll()) {
            result.add(new SyllabusForView(syllabus));
        }
        return result;
    }

    @Override
    public List<SyllabusForView> findAllActive() {
        List<SyllabusForView> result = new ArrayList<>();
        for (Syllabus syllabus : syllabusRepository.findByPublicStatus(EStatus.ACTIVE)) {
            result.add(new SyllabusForView(syllabus));
        }
        return result;
    }


    @Override
    public Syllabus findByTopicCode(String topicCode) {
        return syllabusRepository.findByTopicCode(topicCode);
    }


    @Transactional
    @Override
    public ResponseData create(SyllabusForCreate syllabus) {
        try {
            Syllabus newSyllabus = new Syllabus();
            newSyllabus.setTopicCode(generateTopicCode());
            newSyllabus.setTopicName(syllabus.getTopicName());
            newSyllabus.setVersion(syllabus.getVersion());
            newSyllabus.setPriority(syllabus.getPriority());
            newSyllabus.setTrainingAudience(syllabus.getTrainingAudience());
            newSyllabus.setTechnicalGroup(syllabus.getTechnicalGroup());
            newSyllabus.setTopicOutline(syllabus.getTopicOutline());
            newSyllabus.setPublicStatus(syllabus.getPublicStatus());

            // save new Syllabus
            syllabusRepository.save(newSyllabus);

            syllabus.getTrainingUnitForCreateList().forEach(trainingUnitForCreate -> {
                TrainingUnit newTrainingUnit = new TrainingUnit(trainingUnitForCreate);
                newTrainingUnit.setSyllabus(newSyllabus);

                trainingUnitForCreate.isValid();

                trainingUnitRepository.save(newTrainingUnit);

                trainingUnitForCreate.getTrainingContentForCreateList().forEach(trainingContentForCreate -> {
                    TrainingContent newTrainingContent = new TrainingContent(trainingContentForCreate);
                    newTrainingContent.setTrainingUnit(newTrainingUnit);

                    trainingContentForCreate.isValid();

                    trainingContentRepository.save(newTrainingContent);

                    trainingContentForCreate.getLearningObjectiveForCreateList().forEach(learningObjectiveForCreate -> {
                        LearningObjective newLearningObject = new LearningObjective(learningObjectiveForCreate);
                        newLearningObject.setTrainingContent(newTrainingContent);

                        if (learningObjectiveForCreate.getDescription() == null) {
                            throw new RuntimeException("Description null dataa");
                        }

                        learningObjectiveRepository.save(newLearningObject);

                        SyllabusObject syllabusObject = new SyllabusObject();
                        syllabusObject.setSyllabus(newSyllabus);
                        syllabusObject.setLearningObjective(newLearningObject);
                        syllabusObjectiveRepository.save(syllabusObject);
                    });
                });
            });
            return new ResponseData(0, EM00, null);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseData(-1, e.getMessage(), null);
        }
    }

    @Transactional
    @Override
    public ResponseData update(String topicCode, SyllabusForUpdate syllabus) {
        try {
            Optional<Syllabus> optionalSyllabus = syllabusRepository.findById(topicCode);

            if (optionalSyllabus.isPresent()) {
                Syllabus existingSyllabus = optionalSyllabus.get();
                existingSyllabus.updateFrom(syllabus);
                syllabusRepository.save(existingSyllabus);

                syllabus.getTrainingUnitList().forEach(trainingUnitForUpdate -> {
                    Optional<TrainingUnit> optionalTrainingUnit = trainingUnitRepository.findById(trainingUnitForUpdate.getUnitCode());

                    optionalTrainingUnit.ifPresent(existingTrainingUnit -> {
                        existingTrainingUnit.updateFrom(trainingUnitForUpdate);

                        trainingUnitForUpdate.isValid();

                        trainingUnitRepository.save(existingTrainingUnit);

                        trainingUnitForUpdate.getTrainingContentForUpdateList().forEach(trainingContentForUpdate -> {
                            Optional<TrainingContent> optionalTrainingContent = trainingContentRepository.findById(trainingContentForUpdate.getContentCode());

                            optionalTrainingContent.ifPresent(existingTrainingContent -> {
                                existingTrainingContent.updateFrom(trainingContentForUpdate);

                                trainingContentForUpdate.isValid();

                                trainingContentRepository.save(existingTrainingContent);

                                trainingContentForUpdate.getLearningObjectForUpdateList().forEach(learningObjectiveForUpdate -> {
                                    LearningObjective existingLearningObjective = learningObjectiveRepository.findByCode(learningObjectiveForUpdate.getCode());

                                    if (existingLearningObjective != null) {
                                        existingLearningObjective.updateFrom(learningObjectiveForUpdate);

                                        if (learningObjectiveForUpdate.getDescription() == null || learningObjectiveForUpdate.getDescription().equals("")) {
                                            throw new RuntimeException("Description null dataa");
                                        }

                                        learningObjectiveRepository.save(existingLearningObjective);
                                    }
                                });
                            });
                        });
                    });
                });

                return new ResponseData(0, EM00, null);
            } else {
                return new ResponseData(-1, "Syllabus not found", null);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseData(-1, e.getMessage(), null);
        }
    }

    // Method to duplicate
    @Override
    public ResponseData duplicate(String topicCode) {
        //handle exception
        Syllabus syllabus = findByTopicCode(topicCode);
        if (syllabus != null && syllabus.getPublicStatus() != EStatus.DRAFTING)
        //duplicate if found
        {
            SyllabusForCreate syllabusForCreate = new SyllabusForCreate(syllabus);
            syllabusForCreate.setPublicStatus(EStatus.DRAFTING);

            return create(syllabusForCreate);
        } else
            return new ResponseData(-1, "ERROR", null);
    }


    public ResponseData delete(String syllabus) {
        try {
            // find the syllabus by topic code and version
            Syllabus oldSyllabus = syllabusRepository.findByTopicCode(syllabus);
            if (oldSyllabus == null) {
                return new ResponseData(-1, "Syllabus not found", null);
            }
            // get the list of syllabus objects associated with the syllabus
            List<SyllabusObject> syllabusObjects = syllabusObjectiveRepository.findBySyllabus(oldSyllabus);
            // delete each syllabus object and its corresponding learning objective
            for (SyllabusObject syllabusObject : syllabusObjects) {
                LearningObjective learningObjective = syllabusObject.getLearningObjective();
                syllabusObjectiveRepository.delete(syllabusObject);
                learningObjectiveRepository.delete(learningObjective);
            }
            // get the list of training units associated with the syllabus
            List<TrainingUnit> trainingUnits = trainingUnitRepository.findBySyllabus(oldSyllabus);
            // delete each training unit and its corresponding training contents
            for (TrainingUnit trainingUnit : trainingUnits) {
                List<TrainingContent> trainingContents = trainingContentRepository.findByTrainingUnit(trainingUnit);
                for (TrainingContent trainingContent : trainingContents) {
                    trainingContentRepository.delete(trainingContent);
                }
                List<TrainingProgramSyllabus> trainingPrograms = trainingProgramSyllabusRepository.findBySyllabus(oldSyllabus);
                for (TrainingProgramSyllabus trainingProgram : trainingPrograms) {
                    // Check if topicCode is not null before setting it to null
                    if (trainingProgram.getSyllabus() != null) {
                        trainingProgram.setSyllabus(null);
                        trainingProgramSyllabusRepository.save(trainingProgram);
                    }
                }
                trainingUnitRepository.delete(trainingUnit);
            }
            // delete the syllabus
            syllabusRepository.delete(oldSyllabus);
            return new ResponseData(0, EM00, null);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseData(-1, "Action can't perform", null);
        }
    }

    // Method to generate the topic code
    private String generateTopicCode() {
        // Fetch the latest topic code from the database or any other source
        String latestTopicCode = syllabusRepository.findLatestTopicCode();

        // If no topic code exists, start with a random type and '01'
        if (latestTopicCode == null) {
            char randomType = 'A';
            return String.format("%s01", randomType);
        }

        // Increment the topic code based on the format 'A01', 'S01', 'K05'
        char type = latestTopicCode.charAt(0);
        int sequence = Integer.parseInt(latestTopicCode.substring(1)) + 1;

        // Check if sequence is 9, then increment type and reset sequence to 1
        if (sequence == 100) {
            type = getNextType(type);
            sequence = 1;
        }

        return String.format("%s%02d", type, sequence);
    }

    private char getNextType(char currentType) {
        // Define the possible types
        char[] possibleTypes = {'A', 'H', 'K', 'S'};
        // Find the index of the current type
        int currentIndex = -1;
        for (int i = 0; i < possibleTypes.length; i++) {
            if (possibleTypes[i] == currentType) {
                currentIndex = i;
                break;
            }
        }
        // If the current type is found, return the next type; otherwise, return the first type
        return (currentIndex != -1) ? possibleTypes[(currentIndex + 1) % possibleTypes.length] : possibleTypes[0];
    }
}
