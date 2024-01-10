/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.domain.entity.enums;

public enum EGender {
    M("M"), F("F");
    private final String gender;

    EGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return gender;
    }
}
