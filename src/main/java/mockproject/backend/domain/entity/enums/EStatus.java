/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.domain.entity.enums;

public enum EStatus {
    ACTIVE("ACTIVE"), DRAFTING("DRAFTING"), INACTIVE("INACTIVE");
    private final String status;

    EStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
