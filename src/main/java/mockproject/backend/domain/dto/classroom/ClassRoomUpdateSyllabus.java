package mockproject.backend.domain.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.dto.syllabus.SyllabusForView;

import java.util.List;

/**
 * @author : Hà Mạnh Hùng
 * @mailto : hahunghmh@gmail.com
 * @created : 12/6/2023, Wednesday
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomUpdateSyllabus {
    private Long trainingProgramCode;
    private List<SyllabusForView> syllabus;
}
