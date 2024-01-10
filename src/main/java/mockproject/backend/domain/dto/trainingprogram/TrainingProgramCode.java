package mockproject.backend.domain.dto.trainingprogram;/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 27/11/2023 - 10:30 SA
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.TrainingProgram;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class TrainingProgramCode implements Serializable {
    private Long trainingProgramCode;

    public TrainingProgramCode(TrainingProgram trainingProgramCode) {
        this.trainingProgramCode = trainingProgramCode.getTrainingProgramCode();
    }

}
