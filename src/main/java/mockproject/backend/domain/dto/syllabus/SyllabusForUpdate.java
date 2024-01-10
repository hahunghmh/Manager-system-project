package mockproject.backend.domain.dto.syllabus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.TrainingUnit;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusForUpdate {

    private String topicCode;

    @NotEmpty(message = EM12)
    @Size(max = 60, message = "Syllabus Name must not exceed 60 characters")
    private String topicName;

    private int version;

    // Technical Requirements
    @NotNull(message = EM15)
    private String technicalGroup;

    // Course Objectives
    @NotNull(message = EM16)
    private String topicOutline;

    private String duration;
    private Byte priority;
    private int trainingAudience;
    private LocalDate modifiedDate;
    private String modifiedBy;


    private List<String> outputStandard = new ArrayList<>();
    List<TrainingUnitForUpdate> trainingUnitList = new ArrayList<>();

    public SyllabusForUpdate(Syllabus syllabus) {
        this.topicCode = syllabus.getTopicCode();
        this.topicName = syllabus.getTopicName();
        this.version = syllabus.getVersion();
        this.technicalGroup = syllabus.getTechnicalGroup();
        this.topicOutline = syllabus.getTopicOutline();
        this.priority = syllabus.getPriority();
        this.trainingAudience = syllabus.getTrainingAudience();
        this.duration = syllabus.getDuration() + "";
        this.modifiedBy = syllabus.getLastModifiedBy().getName();
        this.modifiedDate = syllabus.getLastModifiedDate();
        syllabus.getTrainingUnitList().forEach(trainingUnit -> trainingUnitList.add(new TrainingUnitForUpdate(trainingUnit)));
    }
}
