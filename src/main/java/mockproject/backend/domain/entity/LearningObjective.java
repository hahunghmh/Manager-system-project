/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.dto.syllabus.LearningObjectForUpdate;
import mockproject.backend.domain.dto.syllabus.LearningObjectiveForCreate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "learning_objective")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LearningObjective {
    @Id
    @Column(name = "learning_objective_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;//output standard?


    private String name;
    private String type;
    private String description;

    //    mapping
//    Ont learning objective to many syllabus objective
    @OneToMany(mappedBy = "syllabus")
    private List<SyllabusObject> syllabusObjectSet;
    //    Many learning objective to one training content
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traning_content_id", referencedColumnName = "traning_content_id")
    private TrainingContent trainingContent;


    public LearningObjective(LearningObjectiveForCreate learningObjectiveForCreate) {
//        this.code= learningObjectiveForCreate.getCode();
        this.name = learningObjectiveForCreate.getName();
        ;
        this.type = learningObjectiveForCreate.getType();
        this.description = learningObjectiveForCreate.getDescription();
        ;


    }


    public void updateFrom(LearningObjectForUpdate learningObjectiveForUpdate) {
        this.name = learningObjectiveForUpdate.getName();
        this.type = learningObjectiveForUpdate.getType();
        this.description = learningObjectiveForUpdate.getDescription();
        ;

    }
}
