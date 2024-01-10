/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.domain.entity.enums;

//ref 3.4.1 Search class on list
public enum EClassStatus {
    //Status (Planning, Scheduled, Opening, Closed),
    PLANING("PLANING"), SCHEDULED("SCHEDULED"), OPENING("OPENING"), CLOSE("CLOSE");
    private final String classStatus;

    EClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    @Override
    public String toString() {
        return classStatus;
    }
}
