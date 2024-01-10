package mockproject.backend.domain.dto.classroom;/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 24/11/2023 - 4:54 CH
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class ClassRoomForCreateView implements Serializable {
    private String className;
    private Long trainingProgramCode;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String admin;
    private String trainer;
    private String email;
    private String attendee;//user type; intern, fresher,


    public ClassRoomForCreateView(ClassRoomForCreateView classRoom) {
        this.className = classRoom.getClassName();
        this.trainingProgramCode = classRoom.getTrainingProgramCode();
        this.location = classRoom.getLocation();
        this.startDate = classRoom.getStartDate();
        this.endDate = classRoom.getEndDate();
        this.admin = classRoom.getAdmin();
        this.trainer = classRoom.getTrainer();
        this.email = classRoom.getEmail();
    }
}
