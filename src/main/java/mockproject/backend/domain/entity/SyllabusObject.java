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

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SyllabusObject {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    mapping
    //    many syllabus object to one syllabus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_code", referencedColumnName = "topic_code")
    private Syllabus syllabus;

    //    many syllabus object to one learning objective
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_objective_code", referencedColumnName = "learning_objective_code")
    private LearningObjective learningObjective;

    public SyllabusObject(Syllabus syllabus, LearningObjective learningObjective) {
        this.syllabus = syllabus;
        this.learningObjective = learningObjective;
    }

}
