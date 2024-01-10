/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/19/2023 - 1:44 PM
 */


package mockproject.backend.domain.entity.enums;

public enum EUserStatus {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");
    private final String status;

    EUserStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
