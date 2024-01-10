package mockproject.backend.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.enums.EClassStatus;
import mockproject.backend.domain.entity.enums.EClassTime;
import mockproject.backend.domain.entity.enums.EUserType;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : Hà Mạnh Hùng
 * @mailto : hahunghmh@gmail.com
 * @created : 11/27/2023, Monday
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterClassReq {
    private List<String> location;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<EClassTime> classTime;
    private List<EClassStatus> classStatus;
    private List<EUserType> classAttendee;

    private String trainer;
    private String fsu;
    private String trainingProgram;
}

