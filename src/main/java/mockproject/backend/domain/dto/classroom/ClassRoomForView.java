package mockproject.backend.domain.dto.classroom;/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 23/11/2023 - 2:54 CH
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.ClassRoom;
import mockproject.backend.domain.entity.enums.EClassStatus;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ClassRoomForView implements Serializable {

    private String className;
    private String classCode;

    private LocalDate createOn;
    private String createBy;
    private String location;
    private Short duration;
    private String fsu;
    private EClassStatus classStatus;

    public ClassRoomForView(ClassRoom classRoom) {
        this.className = classRoom.getClassName();
        this.classCode = classRoom.getClassCode();
        this.createOn = classRoom.getCreationDate();
        this.createBy = classRoom.getCreatedBy().getName();
        this.duration = classRoom.getDuration();
        this.fsu = classRoom.getFsu();
        this.classStatus = classRoom.getClassStatus();
        this.location = classRoom.getLocation();
    }

}
