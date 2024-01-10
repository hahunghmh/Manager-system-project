package mockproject.backend.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * DTO for {@link User}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForMail implements Serializable {
    private String name;
    private String email;
    private String password;
}