package mockproject.backend.domain.dto.syllabus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.enums.EStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SyllabusForView {

    private String topicCode;
    private String topicName;
    private int version;
    private LocalDate creationDate;
    private String createdBy;
    private int duration;
    private EStatus publicStatus;

    private List<String> outputStandard;

    public SyllabusForView(Syllabus syllabus) {
        this.topicCode = syllabus.getTopicCode();
        this.topicName = syllabus.getTopicName();
        this.createdBy = syllabus.getCreatedBy().getName();
        this.creationDate = syllabus.getCreationDate();
        this.publicStatus = syllabus.getPublicStatus();
        this.duration = syllabus.getDuration();
        this.version = syllabus.getVersion();
    }
}
