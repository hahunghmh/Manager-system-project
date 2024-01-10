/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.enums.EPermission;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_permission")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPermission extends Auditable {
    @Id
    @Column(name = "permission_id")
    private Long permissionId;

    @Enumerated(EnumType.STRING)
    private EPermission permission;

    //    many permissions to one role
    @OneToMany(mappedBy = "role")
    private List<RoleGroup> roleGroupList;

    public UserPermission(Long permissionId, EPermission permission) {
        this.permissionId = permissionId;
        this.permission = permission;
    }
}
