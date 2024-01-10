/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/19/2023 - 4:20 PM
 */


package mockproject.backend.domain.entity.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static mockproject.backend.utils.constant.Constant.FORGOT_EXPIRATION_TIME;


@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTemporary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String code;
    //    no needed
//    private boolean isExpire;
    private LocalDateTime createAt;
    private LocalDateTime expireAt;

    @PrePersist
    protected void prePersist() {
        if (this.createAt == null) createAt = LocalDateTime.now();
        if (this.expireAt == null) expireAt = LocalDateTime.now().plusSeconds(FORGOT_EXPIRATION_TIME);
    }
}
