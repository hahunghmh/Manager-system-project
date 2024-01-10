package mockproject.backend.domain.dto.trainingprogram;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.TrainingProgram;
import mockproject.backend.domain.entity.enums.EStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import static mockproject.backend.utils.constant.ErrorCode.EM35;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TrainingForCreate implements Serializable {

    @NotNull(message = EM35)
    private String name;
    private String createBy;
    private LocalDate createOn;
    private int duration;
    private EStatus status;
    private String listSyllabus;

    public TrainingForCreate(TrainingProgram trainingProgram) {
        this.name = trainingProgram.getName();
        this.duration = trainingProgram.getDuration();
        this.createBy = trainingProgram.getCreatedBy().getName();
        this.createOn = LocalDate.now();
        this.status = trainingProgram.getStatus();
    }
}
