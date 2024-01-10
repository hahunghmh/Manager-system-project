/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/18/2023 - 10:26 PM
 */

package mockproject.backend.domain.entity.enums;

public enum EUserType {
//    Intern / Fresher / Online fee-fresher / Offline fee-fresher.
    IN("IN"), FR("FR"), FRON("FRON"), FROF("FROF");
    private final String userType;

    EUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }
}
