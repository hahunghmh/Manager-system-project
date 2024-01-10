/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/19/2023 - 9:58 AM
 */

package mockproject.backend.controller;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.ResponseDataType;
import mockproject.backend.domain.dto.syllabus.SyllabusForView;
import mockproject.backend.repository.*;
import mockproject.backend.service.SyllabusService;
import mockproject.backend.service.UserService;
import mockproject.backend.service.mail.MailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping
public class TestAPI {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserPermissionRepository userPermissionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleGroupRepository roleGroupRepository;
    @Autowired
    private TrainingProgramRepository trainingProgramRepository;
    @Autowired
    private TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;
    @Autowired
    private LearningObjectiveRepository learningObjectiveRepository;

    @Autowired
    private SyllabusService syllabusService;

    @GetMapping("/abc")
    public String getUser() {
        List<SyllabusForView> views = syllabusService.findAll();

        List<String> outputStandard = learningObjectiveRepository.findDistinctByTrainingContent_TrainingUnit_Syllabus_TopicCode("T69");




        return "ok";
    }

    @GetMapping("/aa")
    public ResponseEntity getList(){
        List<SyllabusForView> views = syllabusService.findAll();

        return ResponseEntity.ok(new ResponseDataType(0,"",views));

    }
}
