package mockproject.backend.domain.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.User;
import mockproject.backend.domain.entity.enums.EGender;
import mockproject.backend.domain.entity.enums.ERole;
import mockproject.backend.domain.entity.enums.EUserStatus;

import java.time.LocalDate;

/**
 * @author : Hà Mạnh Hùng
 * @mailto : hahunghmh@gmail.com
 * @created : 11/23/2023, Thursday
 **/

@NoArgsConstructor
@Getter
@Setter
public class UserForUpdate {
    private long userId;
    private String name;
    private String email;
    private String phone;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dob;
    private EGender gender;
    private ERole role;
    private EUserStatus status;

    public UserForUpdate(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.dob = user.getDob();
        this.gender = user.getGender();
        this.role = user.getRole().getRole();
        this.status = user.getStatus();
    }
}
