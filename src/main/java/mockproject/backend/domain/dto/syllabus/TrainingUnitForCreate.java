package mockproject.backend.domain.dto.syllabus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.dto.syllabus.TrainingContentForCreate;
import mockproject.backend.domain.dto.syllabus.TrainingUnitForUpdate;
import mockproject.backend.domain.entity.TrainingUnit;

import java.util.ArrayList;
import java.util.List;

import static mockproject.backend.utils.constant.ErrorCode.EM17;
import static mockproject.backend.utils.constant.ErrorCode.EM23;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingUnitForCreate {

//    private Long unitCode;

    private String unitName;

    private int dayNumber;

    List<TrainingContentForCreate> trainingContentForCreateList = new ArrayList<>();

    public TrainingUnitForCreate(TrainingUnit trainingUnit) {
        this.unitName = trainingUnit.getUnitName();
        this.dayNumber = trainingUnit.getDayNumber();

        trainingUnit.getTrainingContentList().forEach(trainingContent ->
                trainingContentForCreateList.add(new TrainingContentForCreate(trainingContent))
        );
    }
}
