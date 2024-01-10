package mockproject.backend.service;

import mockproject.backend.domain.dto.FilterClassReq;
import mockproject.backend.domain.dto.PageConfigClassFilter;
import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.classroom.*;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramForView;
import mockproject.backend.domain.dto.user.UserForView;
import mockproject.backend.domain.entity.*;
import mockproject.backend.domain.entity.enums.EStatus;
import mockproject.backend.domain.entity.enums.EUserType;
import mockproject.backend.repository.*;
import mockproject.backend.repository.other.FSURepository;
import mockproject.backend.repository.other.LocationRepository;
import mockproject.backend.service.filter.ClassRoomSpecifications;
import mockproject.backend.service.template.ClassRoomServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static mockproject.backend.utils.constant.ErrorCode.EM00;

@Service
public class ClassRoomService implements ClassRoomServiceInterface {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private ClassRoomRepository classRepository;

    @Autowired
    private TrainingProgramService trainingProgramService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassUserRepository classUserRepository;

    @Autowired
    private SyllabusRepository syllabusRepository;

    @Autowired
    private TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;
    @Autowired
    private FSURepository fsuRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserService userService;

    @Override
    public ClassRoom getById(Long id) {
        Optional<ClassRoom> classRoom = classRoomRepository.findById(id);
        return classRoom.map(value -> modelMapper.map(value, ClassRoom.class)).orElse(null);
    }

    @Override
    public ResponseData createClass(ClassRoomForCreateView createClass) {
        ResponseData result = new ResponseData();
        try {

            ClassRoom classRoom = new ClassRoom(createClass);

            TrainingProgram trainingProgram = trainingProgramService.getTrainingProgramByCode(createClass.getTrainingProgramCode());

            StringBuilder classCode = new StringBuilder();
            classCode.append(createClass.getLocation());
            classCode.append("_");

            if (createClass.getStartDate() != null)
                classCode.append(String.format("%02d", createClass.getStartDate().getYear() % 100));
            else
                classCode.append(String.format("%02d", LocalDate.now().getYear() % 100));

            classCode.append("_");
            classCode.append(EUserType.valueOf(createClass.getAttendee()));
            classCode.append("_");
            classCode.append(createClass.getTrainingProgramCode());
            classCode.append("_");

            long numberOfTrainingCode = trainingProgramRepository.countByTrainingProgramCode(createClass.getTrainingProgramCode());
            classCode.append(String.format("%02d", numberOfTrainingCode % 100));

            classRoom.setTrainingProgram(trainingProgram);
            classRoom.setClassCode(classCode.toString());

            //save
            classRepository.save(classRoom);

//        save admin+ trainer???
            ClassUser classUser = new ClassUser();
            classUser.setTClassRoom(classRoom);
            classUser.setUserType(EUserType.valueOf(createClass.getAttendee()));
            classUserRepository.save(classUser);

            long adminId = Long.parseLong(createClass.getAdmin().split("_")[0]);
            String adminEmail = createClass.getAdmin().split("_")[1];

            User admin = userRepository.findByUserIdAndEmail(adminId, adminEmail);

            if (createClass.getTrainer() != null) {
                long trainerId = Long.parseLong(createClass.getTrainer().split("_")[0]);
                String trainerEmail = createClass.getTrainer().split("_")[1];

                User trainer = userRepository.findByUserIdAndEmail(trainerId, trainerEmail);
                trainer.setClassUser(classUser);
                userRepository.save(trainer);
            }

            admin.setClassUser(classUser);
            userRepository.save(admin);
//duplicate
        } catch (Exception ex) {
            return new ResponseData(-1, "Error", null);
        }
        return result;
    }

    @Override
    public ResponseData changeTrainingProgramForClass(ClassRoomChangeTrainingProgram form) {
        ClassRoom classRoom = getById(form.getClassId());

        if (classRoom == null) return new ResponseData(-1, "Update fail", null);

        TrainingProgram trainingProgram = trainingProgramService.getTrainingProgramByCode(form.getTrainingProgramCode());

        if (trainingProgram == null) return new ResponseData(-1, "Update fail", null);

        classRoom.setTrainingProgram(trainingProgram);

        classRoomRepository.save(classRoom);

        return new ResponseData(0, "Update success", null);
    }


    @Override
    public ResponseData deleteOrUpdateSyllabus(ClassRoomUpdateSyllabus form) {

        TrainingProgram trainingProgramOld = trainingProgramService.getTrainingProgramByCode(form.getTrainingProgramCode());
        if (trainingProgramOld == null) return new ResponseData(-1, "Update fail", null);
        List<String> syllabusID = trainingProgramSyllabusRepository.getSyllabusById(form.getTrainingProgramCode());
        List<String> idNew = new ArrayList<>();
        form.getSyllabus().forEach(id -> idNew.add(id.getTopicCode()));
//        kiem tra xem co trung nhau hay khong
        if (!hasSameContent(idNew, syllabusID)) {
//            khi khong trung nhau clone ra mot ban trainingProgram
            TrainingProgram duplicateTrainingProgram = new TrainingProgram();
            duplicateTrainingProgram.setName(trainingProgramOld.getName() + "_Copy");
            duplicateTrainingProgram.setStatus(EStatus.ACTIVE);
            duplicateTrainingProgram.setDuration(trainingProgramOld.getDuration());
            duplicateTrainingProgram = trainingProgramRepository.save(duplicateTrainingProgram);
            TrainingProgram trainingProgram = trainingProgramRepository.save(duplicateTrainingProgram);
            List<Syllabus> syllabusList = new ArrayList<>();
            for (String string : idNew) {
                Syllabus syllabus = syllabusRepository.findByTopicCode(string);
                if (syllabus != null) {
                    syllabusList.add(syllabus);
                } else {
                    return new ResponseData(-1, "Update fail", null);
                }
            }
            for (int i = 0; i < idNew.size(); i++) {
                TrainingProgramSyllabus newTrain = new TrainingProgramSyllabus(trainingProgram, syllabusList.get(i));
                trainingProgramSyllabusRepository.save(newTrain);
            }
            return new ResponseData(0, "success", modelMapper.map(duplicateTrainingProgram, TrainingProgramForView.class));
        }
        return new ResponseData(-1, "fail", null);
    }

    public ResponseData advancedFilter(PageConfigClassFilter pageConfigClassFilter) {
        Specification<ClassRoom> spec = Specification.where(null);
        //search by keywords;
        List<String> keys = pageConfigClassFilter.getKeyword();
        if (Objects.nonNull(keys) && !keys.isEmpty()) {
            for (String key : keys) {
                spec = spec.and(ClassRoomSpecifications.searchByKey(key));
            }
        }
//filter
        FilterClassReq filter = pageConfigClassFilter.getFilterClassReq();
        if (Objects.nonNull(filter)) {
            //class status
            if (Objects.nonNull(filter.getClassStatus()) && !filter.getClassStatus().isEmpty())
                spec = spec.and(ClassRoomSpecifications.filterByStatus(filter.getClassStatus()));
//class time
            if (Objects.nonNull(filter.getClassTime()) && !filter.getClassTime().isEmpty())
                spec = spec.and(ClassRoomSpecifications.filterByClassTime(filter.getClassTime()));
//class anteendee
            if (Objects.nonNull(filter.getClassAttendee()) && !filter.getClassAttendee().isEmpty())
                spec = spec.and(ClassRoomSpecifications.filterByAttendee(filter.getClassAttendee()));

//            startdate
            if (Objects.nonNull(filter.getStartDate()))
                spec = spec.and(ClassRoomSpecifications.filterByAfterDateTime(filter.getStartDate()));
//end date
            if (Objects.nonNull(filter.getEndDate()))
                spec = spec.and((ClassRoomSpecifications.filterByBeforeDateTime(filter.getEndDate())));
//            fsu
            if (Objects.nonNull(filter.getFsu()) && !filter.getFsu().isEmpty())
                spec = spec.and(ClassRoomSpecifications.filterByFsu(filter.getFsu()));
//            trainer
            if (Objects.nonNull(filter.getTrainer()) && !filter.getTrainer().isEmpty())
                spec = spec.and(ClassRoomSpecifications.filterByTrainer(filter.getTrainer()));
//      location
            if (Objects.nonNull(filter.getLocation()) && !filter.getLocation().isEmpty()) {
                List<String> locations = filter.getLocation();
                for (String location : locations) {
                    spec = spec.and(ClassRoomSpecifications.filterByLocation(location));
                }
            }
        }
        Page<ClassRoomForView> resultSet = classRepository.findAll(spec, pageConfigClassFilter.getPageableConfig()).map(ClassRoomForView::new);
        return new ResponseData(0, "OK", resultSet);
    }

    @Override
    public Page<ClassRoom> findByKey(String key, Pageable pageRequest) {
        Specification<ClassRoom> spec = Specification.where(null);
        if (Objects.nonNull(key) && !key.isEmpty()) {
            spec = spec.and(ClassRoomSpecifications.searchByKey(key));
        }
        return classRepository.findAll(spec, pageRequest);
    }

    //    for create class
    @Override
    public ResponseData getClassInfo() {
        List<UserForView> adminList = userService.getAdmin();
        List<UserForView> trainerList = userService.getTrainer();
        List<UserForView> emailList = userService.getEmail();

        List<ClassRoomForView> fsuList = getFSU();
        List<TrainingProgramForView> trainingProgramCodeList = trainingProgramService.findAll();

        ClassRoomDetailForCreate classRoomDetailForCreate = new ClassRoomDetailForCreate();
        classRoomDetailForCreate.setAdmin(adminList);
        classRoomDetailForCreate.setTrainer(trainerList);
        classRoomDetailForCreate.setEmail(emailList);
        classRoomDetailForCreate.setFsu(fsuList);
        classRoomDetailForCreate.setTrainingProgramCodeList(trainingProgramCodeList);

        ResponseData resultRes = new ResponseData(0, EM00, classRoomDetailForCreate);

        return resultRes;
    }


    @Override
    public List<ClassRoomForView> getFSU() {
        List<ClassRoomForView> result = new ArrayList<>();
        List<ClassRoom> classRooms = trainingProgramRepository.findAllFsu();
        for (ClassRoom classRoom : classRooms) {
            result.add(modelMapper.map(classRoom, ClassRoomForView.class));
        }
        return result;
    }

    public ResponseData getLocation() {
        List<String> location = locationRepository.getAllCode();
        return new ResponseData(0, "", location);
    }


    // private method
    private static boolean hasSameContent(List<String> list1, List<String> list2) {
        // Kiểm tra xem có cùng nội dung không, không phụ thuộc vào thứ tự
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    @Override
    public ResponseData getClassDetailByCode(String name, String code) {
        ResponseData responseData;
        Optional<ClassRoom> classRoom = classRoomRepository.findByClassNameAndClassCode(name, code);

        if (classRoom.isPresent()) {
            responseData = new ResponseData(0, "Successfully", new ClassRoomForUpdate(classRoom.get()));
        } else {
            responseData = new ResponseData(-1, "Class not found", null);
        }
        return responseData;
    }
}
