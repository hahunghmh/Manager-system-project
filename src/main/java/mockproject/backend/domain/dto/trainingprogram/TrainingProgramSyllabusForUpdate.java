package mockproject.backend.domain.dto.trainingprogram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.TrainingProgramSyllabus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramSyllabusForUpdate implements Serializable {
    private Long id;
    private Integer sequence;
    private Long trainingCode;
    private String syllabusCode;


    public TrainingProgramSyllabusForUpdate(TrainingProgramSyllabus trainingProgramSyllabus) {
        this.id = trainingProgramSyllabus.getId();
        this.sequence = trainingProgramSyllabus.getSequence();
        this.trainingCode = trainingProgramSyllabus.getTrainingProgram().getTrainingProgramCode();
        this.syllabusCode = trainingProgramSyllabus.getSyllabus().getTopicCode();
    }
}
