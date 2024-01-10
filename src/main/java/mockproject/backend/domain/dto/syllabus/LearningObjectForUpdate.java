package mockproject.backend.domain.dto.syllabus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.LearningObjective;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearningObjectForUpdate implements Serializable {
    private Long code;
    private String name;
    private String type;
    private String description;

    public LearningObjectForUpdate(LearningObjective learningObjective) {
        this.code = learningObjective.getCode();
        this.name = learningObjective.getName();
        this.type = learningObjective.getType();
        this.description = learningObjective.getDescription();
    }

}
