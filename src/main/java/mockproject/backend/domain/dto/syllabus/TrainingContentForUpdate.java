package mockproject.backend.domain.dto.syllabus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.LearningObjective;
import mockproject.backend.domain.entity.TrainingContent;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingContentForUpdate {

    private Long contentCode;

    @NotNull(message = EM20)
    private int duration;

    @NotNull(message = EM21)

    private int deliveryType;

    @NotNull(message = EM18)
    private String content;
    private String note;
    private String trainingFormat;

    List<LearningObjectForUpdate> learningObjectForUpdateList = new ArrayList<>();

    public TrainingContentForUpdate(TrainingContent trainingContent) {
        this.contentCode = trainingContent.getContentCode();
        this.content = trainingContent.getContent();
        this.deliveryType = trainingContent.getDeliveryType();
        this.duration = trainingContent.getDuration();
        this.trainingFormat = trainingContent.getTrainingFormat();
        this.note = trainingContent.getNote();

        trainingContent.getLearningObjectiveSet().forEach(learningObjective -> learningObjectForUpdateList.add(new LearningObjectForUpdate(learningObjective)));
    }
    public void isValid() {
        if (content == null || content.equals("")) {
            throw new RuntimeException(EM18);
        }
        if (deliveryType <0 || deliveryType>1) {
            throw new RuntimeException(EM21);
        }
        if (duration <= 0) {
            throw new RuntimeException(EM20);
        }

    }
}
