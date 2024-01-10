package mockproject.backend.domain.dto.trainingprogram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.dto.syllabus.SyllabusForView;
import mockproject.backend.domain.entity.TrainingProgram;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link mockproject.backend.domain.entity.TrainingProgram}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramForClassCreate implements Serializable {
    private String lastModifiedBy;
    private LocalDate lastModifiedDate;
    private String name;
    private int duration = 0;

    List<SyllabusForView> syllabus = new ArrayList<>();

    public TrainingProgramForClassCreate(TrainingProgram trainingProgram) {
        this.lastModifiedBy = trainingProgram.getLastModifiedBy().getName();
        this.lastModifiedDate = trainingProgram.getLastModifiedDate();
        this.name = trainingProgram.getName();
        this.duration = trainingProgram.getDuration();
    }
}