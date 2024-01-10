/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 12/14/2023 - 6:21 PM
 */


package mockproject.backend.domain.dto.classroom;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.dto.trainingprogram.TrainingForUpdate;
import mockproject.backend.domain.entity.ClassRoom;
import mockproject.backend.domain.entity.enums.EClassStatus;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomForUpdate implements Serializable {

    private String className;
    private String classCode;

    private Short duration;
    private EClassStatus classStatus;

    private String lastModifiedBy;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate lastModifiedOn;

    private TrainingForUpdate trainingProgram;


    public ClassRoomForUpdate(ClassRoom classRoom) {
        this.className = classRoom.getClassName();
        this.classCode = classRoom.getClassCode();
        this.duration = classRoom.getDuration();
        this.classStatus = classRoom.getClassStatus();
        this.lastModifiedBy = classRoom.getLastModifiedBy().getName();
        this.lastModifiedOn = classRoom.getLastModifiedDate();
        if (classRoom.getTrainingProgram() != null) {
            this.trainingProgram = new TrainingForUpdate(classRoom.getTrainingProgram());
        } else {
            this.trainingProgram = new TrainingForUpdate();
        }
    }
}
