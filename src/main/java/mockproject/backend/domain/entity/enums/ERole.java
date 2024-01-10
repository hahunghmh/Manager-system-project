/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.domain.entity.enums;

public enum ERole {
    SUPERADMIN("SUPERADMIN"), ADMIN("ADMIN"), TRAINER("TRAINER");
    private final String role;

    ERole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
