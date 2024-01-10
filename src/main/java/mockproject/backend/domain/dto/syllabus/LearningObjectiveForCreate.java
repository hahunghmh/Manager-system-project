package mockproject.backend.domain.dto.syllabus;/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 03/12/2023 - 3:55 CH
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.dto.syllabus.LearningObjectForUpdate;
import mockproject.backend.domain.entity.LearningObjective;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LearningObjectiveForCreate implements Serializable {
    //    private Long code;
    private String name;
    private String type;
    private String description;
//    private Long trainingContentCode;

    public LearningObjectiveForCreate(LearningObjectForUpdate learningObject) {
        this.name = learningObject.getName();
        this.description = learningObject.getDescription();
        this.type = learningObject.getType();
    }

    public LearningObjectiveForCreate(LearningObjective learningObject) {
        this.name = learningObject.getName();
        this.description = learningObject.getDescription();
        this.type = learningObject.getType();
    }
}
