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
import mockproject.backend.domain.dto.syllabus.SyllabusForCreate;
import mockproject.backend.domain.dto.syllabus.SyllabusForUpdate;
import mockproject.backend.domain.entity.enums.EStatus;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//audit
public class Syllabus extends Auditable{
    @Id
    @Column(name = "topic_code")
    private String topicCode;

    private String topicName;

    private String technicalGroup;

    private int duration;
    private int version;

    private int trainingAudience;

    @Column(length = 1000)
    private String topicOutline;

    private String trainingMaterials;
    private String trainingPrinciples;

    private Byte priority;

    @Enumerated(EnumType.STRING)
    private EStatus publicStatus;

    //mapping
    //    many syllabus to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    //    one syllabus to many syllabus objective
    @OneToMany(mappedBy = "learningObjective")
    private List<SyllabusObject> syllabusObjectSet;

    //    one syllabus to many training unit
    @OneToMany(mappedBy = "syllabus")
    private List<TrainingUnit> trainingUnitList;

    //    one syllabus to many traning program syllabus
    @OneToMany(mappedBy = "syllabus")
    private List<TrainingProgramSyllabus> trainingProgramSyllabusSet;


    public Syllabus(SyllabusForCreate syllabus, String topicCode) {
        this.topicCode = topicCode;
        this.topicName = syllabus.getTopicName();
        this.version = syllabus.getVersion();
        this.technicalGroup = syllabus.getTechnicalGroup();
        this.topicOutline = syllabus.getTopicOutline();
    }

    public void updateFrom(SyllabusForUpdate syllabus) {
        this.topicCode = syllabus.getTopicCode();
        this.topicName = syllabus.getTopicName();
        this.version = syllabus.getVersion();
        this.technicalGroup = syllabus.getTechnicalGroup();
        this.topicOutline = syllabus.getTopicOutline();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Syllabus syllabus = (Syllabus) o;
        return Objects.equals(topicCode, syllabus.topicCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicCode);
    }
}
