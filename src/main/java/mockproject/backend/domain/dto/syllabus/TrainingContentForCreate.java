package mockproject.backend.domain.dto.syllabus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.TrainingContent;

import java.util.ArrayList;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentForCreate {

    private String content;
    private int deliveryType;
    private int duration;
    private String note;
    private String trainingFormat;

    List<LearningObjectiveForCreate> learningObjectiveForCreateList = new ArrayList<>();

    public TrainingContentForCreate(TrainingContent trainingContent) {
        this.duration = trainingContent.getDuration();
        this.deliveryType = trainingContent.getDeliveryType();
        this.content = trainingContent.getContent();
        this.note = trainingContent.getNote();
        this.trainingFormat = trainingContent.getTrainingFormat();

        trainingContent.getLearningObjectiveSet().forEach(learningObjective ->
                learningObjectiveForCreateList.add(new LearningObjectiveForCreate(learningObjective))
        );
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
