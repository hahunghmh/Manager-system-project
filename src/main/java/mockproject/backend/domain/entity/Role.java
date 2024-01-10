/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/20/2023 - 1:52 PM
 */


package mockproject.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.enums.ERole;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    //many users to one role
    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToMany(mappedBy = "role")
    private List<User> userList;

    @OneToMany(mappedBy = "role")
    private List<RoleGroup> roleGroupList;


}
