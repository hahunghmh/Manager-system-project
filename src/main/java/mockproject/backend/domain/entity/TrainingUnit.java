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
import mockproject.backend.domain.dto.trainingprogram.TrainingUnitForCreate;
import mockproject.backend.domain.dto.syllabus.TrainingUnitForUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "training_unit")


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TrainingUnit {
    @Id
    @Column(name = "training_unit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitCode;

    @Column(nullable = false)
    private String unitName;

    private int dayNumber;

    //    mapping
    //    many training unit to one syllabus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_code", referencedColumnName = "topic_code")
    private Syllabus syllabus;

    //    one training unit to many training contentn
    @OneToMany(mappedBy = "trainingUnit")
    private List<TrainingContent> trainingContentList;

    public TrainingUnit(TrainingUnitForCreate trainingUnitForCreate) {
        this.unitName = trainingUnitForCreate.getUnitName();
        this.dayNumber = trainingUnitForCreate.getDayNumber();
    }

    public void updateFrom(TrainingUnitForUpdate trainingUnitForUpdate) {
        this.unitName = trainingUnitForUpdate.getUnitName();
        this.dayNumber = trainingUnitForUpdate.getDayNumber();
    }
}
