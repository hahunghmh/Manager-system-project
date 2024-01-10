package mockproject.backend.domain.dto.syllabus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.dto.trainingprogram.TrainingUnitForCreate;
import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.enums.EStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.*;

@Getter
@Setter
@NoArgsConstructor
public class SyllabusForCreate implements Serializable {
    //    private String topicCode;
    @NotEmpty(message = EM12)
    @Size(max = 60, message = "Syllabus Name must not exceed 60 characters")
    private String topicName;

    private int version;

    @NotNull(message = EM15)
    private String technicalGroup;

    private String topicOutline;

    private int trainingAudience;
    private EStatus publicStatus;
    private Byte priority;
    List<TrainingUnitForCreate> trainingUnitForCreateList = new ArrayList<>();

    public SyllabusForCreate(Syllabus syllabus) {
        this.topicName = syllabus.getTopicName() + "_copy";
        this.version = syllabus.getVersion();
        this.technicalGroup = syllabus.getTechnicalGroup();
        this.topicOutline = syllabus.getTopicOutline();
        this.priority = syllabus.getPriority();
        this.publicStatus = syllabus.getPublicStatus();
        this.trainingAudience = syllabus.getTrainingAudience();
        syllabus.getTrainingUnitList().forEach(trainingUnit ->
                trainingUnitForCreateList.add(new TrainingUnitForCreate(trainingUnit))
        );

    }
}
