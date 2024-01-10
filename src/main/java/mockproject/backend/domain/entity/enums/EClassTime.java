/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.domain.entity.enums;

//ref 3.4.1 Search class on list
public enum EClassTime {
    //    Class time (Morning, Noon, Night, Online)
    MORNING("MORNING"), NOON("NOON"), NIGHT("NIGHT"), ONLINE("ONLINE");
    private final String classTime;

     EClassTime(String classTime) {
        this.classTime = classTime;
    }

    @Override
    public String toString() {
        return classTime;
    }
}
