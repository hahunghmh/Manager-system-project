/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/25/2023 - 10:51 AM
 */


package mockproject.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionForUpdate implements Serializable {
    private Integer[] roleSuperAdmin;
    private Integer[] roleAdmin;
    private Integer[] roleTrainer;
}
