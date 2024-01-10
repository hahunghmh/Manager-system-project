package mockproject.backend.domain.dto.user;

import lombok.Value;
import mockproject.backend.domain.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserLoginForm implements Serializable {
    @Email(message = "Email is require")
    @NotEmpty(message = "Email cannot be empty")
    String email;

    @NotEmpty(message = "Password cannot be empty")
    String password;
}