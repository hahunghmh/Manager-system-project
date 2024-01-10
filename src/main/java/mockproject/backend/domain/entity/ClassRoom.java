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
import mockproject.backend.domain.dto.classroom.ClassRoomForCreateView;
import mockproject.backend.domain.entity.enums.EClassStatus;
import mockproject.backend.domain.entity.enums.EClassTime;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ClassRoom extends Auditable {
    @Id
    @Column(name = "class_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;

    private String className;
    private String classCode;
    private Short duration;

    //    status, location,fsu
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    //    FSU: prefix tag name
    private String fsu;

    //    ref 3.4.1 class status
    @Enumerated(EnumType.STRING)
    private EClassStatus classStatus;
    //    ref 3.4.1 class time
    @Enumerated(EnumType.STRING)
    private EClassTime classTime;

    //mapping
//    one class to many Class User
    @OneToMany(mappedBy = "tClassRoom")
    private List<ClassUser> classUserList;

    //    many Class to one Training program
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_code", referencedColumnName = "training_code")
    private TrainingProgram trainingProgram;


    //    constructor
    public ClassRoom(ClassRoomForCreateView createClass) {
        this.className = createClass.getClassName();
        this.location = createClass.getLocation();
        this.startDate = createClass.getStartDate();
        this.endDate = createClass.getEndDate();
    }
}
