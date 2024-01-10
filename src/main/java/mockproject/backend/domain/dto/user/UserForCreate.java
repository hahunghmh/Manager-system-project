package mockproject.backend.domain.dto.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import mockproject.backend.domain.entity.User;
import mockproject.backend.domain.entity.enums.EGender;
import mockproject.backend.domain.entity.enums.ERole;
import mockproject.backend.domain.entity.enums.EUserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link User}
 */
@Getter
@Setter
public class UserForCreate implements Serializable {
    @NotEmpty
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "[0-9]{10}",message = "Phone wrong format!")
    private String phone;
    private LocalDate dob;
    private EGender gender;
    private ERole role;
    private EUserStatus status;
}