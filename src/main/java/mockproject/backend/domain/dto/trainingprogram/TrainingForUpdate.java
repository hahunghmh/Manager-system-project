package mockproject.backend.domain.dto.trainingprogram;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.TrainingProgram;
import mockproject.backend.domain.entity.TrainingProgramSyllabus;
import mockproject.backend.domain.entity.enums.EStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TrainingForUpdate implements Serializable {
    private Long trainingProgramCode;
    private String name;
    private String modifiled;
    private String createBy;
    private LocalDate createOn;
    private LocalDate lastModifiedOn;
    private int duration;
    private EStatus status;
    @NotNull
    private String listSyllabus;

    public TrainingForUpdate(TrainingProgram trainingProgram) {
        this.trainingProgramCode = trainingProgram.getTrainingProgramCode();
        this.name = trainingProgram.getName();
        this.duration = trainingProgram.getDuration();
        this.modifiled = trainingProgram.getLastModifiedBy().getName();
        this.createBy = trainingProgram.getCreatedBy().getName();
        this.createOn = trainingProgram.getCreationDate();
        this.lastModifiedOn = trainingProgram.getLastModifiedDate();


        this.status = trainingProgram.getStatus();
        this.listSyllabus = convertSyllabusListToString(trainingProgram.getTrainingProgramSyllabusList());
    }

    private String convertSyllabusListToString(List<TrainingProgramSyllabus> syllabusList) {
        return syllabusList.stream()
                .map(TrainingProgramSyllabus::getSyllabus)
                .filter(syllabus -> syllabus != null && syllabus.getTopicCode() != null)
                .map(Syllabus::getTopicCode)
                .collect(Collectors.joining(", "));
    }

}


