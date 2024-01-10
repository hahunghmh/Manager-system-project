package mockproject.backend.domain.dto.syllabus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.TrainingContent;
import mockproject.backend.domain.entity.TrainingUnit;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.EM17;
import static mockproject.backend.utils.constant.ErrorCode.EM23;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingUnitForUpdate {

    @NotEmpty(message = EM17)
    private Long unitCode;

    private String unitName;

    private int dayNumber;

    List<TrainingContentForUpdate> trainingContentForUpdateList = new ArrayList<>();

    public TrainingUnitForUpdate(TrainingUnit trainingUnit) {
        this.unitCode = trainingUnit.getUnitCode();
        this.unitName = trainingUnit.getUnitName();
        this.dayNumber = trainingUnit.getDayNumber();

        trainingUnit.getTrainingContentList().forEach(trainingContent -> trainingContentForUpdateList.add(new TrainingContentForUpdate(trainingContent)));
    }

    public void isValid() {
        if (unitName == null || unitName.equals("")) {
            throw new RuntimeException(EM17);
        }
        if (dayNumber <= 0) {
            throw new RuntimeException(EM23);
        }
    }
}
