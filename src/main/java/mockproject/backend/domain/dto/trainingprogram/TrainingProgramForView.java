package mockproject.backend.domain.dto.trainingprogram;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.TrainingProgram;
import mockproject.backend.domain.entity.enums.EStatus;


import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TrainingProgramForView implements Serializable {

    private Long trainingProgramCode;
    private String name;
    private String createBy;
    private LocalDate createOn;
    private int duration;
    private EStatus status;


    public TrainingProgramForView(TrainingProgram trainingProgram) {
        this.trainingProgramCode = trainingProgram.getTrainingProgramCode();
        this.name = trainingProgram.getName();
        this.duration = trainingProgram.getDuration();
        this.createBy=trainingProgram.getCreatedBy().getName();
        this.createOn=LocalDate.now();
        this.status=trainingProgram.getStatus();
    }
}
