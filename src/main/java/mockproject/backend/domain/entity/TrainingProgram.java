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
import mockproject.backend.domain.entity.enums.EStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "training_program")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class TrainingProgram extends Auditable {
    @Id
    @Column(name = "training_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingProgramCode;

    @Column(nullable = false)
    private String name;

    private LocalDate startTime;
    //no of days
    private int duration;

    @Enumerated(EnumType.STRING)
    private EStatus status;


    //    mapping
    //    one training program to many class
    @OneToMany(mappedBy = "trainingProgram")
    private List<ClassRoom> classRoomList;

    //    many training program to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    //    one training program to many training program syllabus
    @OneToMany(mappedBy = "trainingProgram")
    private List<TrainingProgramSyllabus> trainingProgramSyllabusList;


}
