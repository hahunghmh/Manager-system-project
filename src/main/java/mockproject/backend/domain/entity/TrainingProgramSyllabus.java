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
@Table(name = "training_program_syllabus")


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TrainingProgramSyllabus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sequence;


    //    mapping
//    many training program syllabus to one training program
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_code", referencedColumnName = "training_code")
    private TrainingProgram trainingProgram;

    //  many training program syllabus to one syllabus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_code", referencedColumnName = "topic_code")
    private Syllabus syllabus;


    public TrainingProgramSyllabus(TrainingProgram trainingProgram,Syllabus syllabus) {
        this.syllabus = syllabus;
        this.trainingProgram = trainingProgram;
    }
}
