package mockproject.backend.domain.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author : Hà Mạnh Hùng
 * @mailto : hahunghmh@gmail.com
 * @created : 12/6/2023, Wednesday
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomChangeTrainingProgram implements Serializable {
    private Long trainingProgramCode;
    private Long classId;
}
