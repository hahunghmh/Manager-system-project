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
import mockproject.backend.domain.dto.syllabus.TrainingContentForCreate;
import mockproject.backend.domain.dto.syllabus.TrainingContentForUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TrainingContent {
    @Id
    @Column(name = "traning_content_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentCode;

    private String content;
    private int deliveryType;
    private int duration;
    private String trainingFormat;
    private String note;

    //mapping
    //    many training content to one training unit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_unit_id", referencedColumnName = "training_unit_id")
    //@JoinColumn(name = "tranining_content",referencedColumnName = "")
    private TrainingUnit trainingUnit;

    //    one training content to many learning objective
    @OneToMany(mappedBy = "trainingContent")
    private List<LearningObjective> learningObjectiveSet;


    public TrainingContent(TrainingContentForCreate trainingContentForCreate) {
//        this.contentCode= trainingContentForCreate.getContentcode();
        this.duration= trainingContentForCreate.getDuration();;
        this.deliveryType= trainingContentForCreate.getDeliveryType();

        this.content=trainingContentForCreate.getContent();
        this.note=trainingContentForCreate.getNote();
        this.trainingFormat=trainingContentForCreate.getTrainingFormat();

    }

    public void updateFrom(TrainingContentForUpdate trainingContentForUpdate) {
        this.duration= trainingContentForUpdate.getDuration();;
        this.deliveryType= trainingContentForUpdate.getDeliveryType();

        this.content=trainingContentForUpdate.getContent();
        this.note=trainingContentForUpdate.getNote();
        this.trainingFormat=trainingContentForUpdate.getTrainingFormat();
    }
}
