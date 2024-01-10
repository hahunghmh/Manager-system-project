package mockproject.backend.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.User;
import mockproject.backend.domain.entity.enums.EPermission;
import mockproject.backend.domain.entity.enums.ERole;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link User}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserLoginResponse implements Serializable {

    private String name;
    private String email;
    private String token;
    private ERole role;
    private List<EPermission> permissionList;

    public UserLoginResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}