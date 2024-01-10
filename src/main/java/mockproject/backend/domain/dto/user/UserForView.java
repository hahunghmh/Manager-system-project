package mockproject.backend.domain.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.User;
import mockproject.backend.domain.entity.enums.EGender;
import mockproject.backend.domain.entity.enums.ERole;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author : Hà Mạnh Hùng
 * @mailto : hahunghmh@gmail.com
 * @created : 11/20/2023, Monday
 **/

/**
 * DTO for {@link User}
 */
@NoArgsConstructor
@Getter
@Setter
public class UserForView implements Serializable {
    private Long userId;
    private String name;
    private String email;
    private LocalDate dob;
    private EGender gender;
    private ERole role;


    private String phone;
    private String status;


    public UserForView(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.dob = user.getDob();
        this.gender = user.getGender();
        this.role = user.getRole().getRole();
        this.status = user.getStatus().toString();
        this.phone = user.getPhone();
    }
}
