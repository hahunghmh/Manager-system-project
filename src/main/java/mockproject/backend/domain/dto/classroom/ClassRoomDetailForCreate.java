package mockproject.backend.domain.dto.classroom;/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 28/11/2023 - 2:31 CH
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.dto.trainingprogram.TrainingProgramForView;
import mockproject.backend.domain.dto.user.UserForView;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ClassRoomDetailForCreate implements Serializable {

    private List<UserForView> admin;
    private List<UserForView> trainer;
    private List<UserForView> email;
    private List<TrainingProgramForView> trainingProgramCodeList;
    private List<ClassRoomForView> fsu;
//    private List<>...


}
