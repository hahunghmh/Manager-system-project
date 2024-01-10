/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/26/2023 - 10:20 PM
 */


package mockproject.backend.utils.constant;

import static mockproject.backend.utils.constant.ApiUrl.API;

public interface ClassUrlAPI {

    String CLASS = API + "/class";
    String CLASS_CREATE = CLASS + "/create";
    String CLASS_UPDATE = CLASS + "/update";
    String CLASS_DELETE = CLASS + "/delete/{id}";
    String CLASS_GET_BY_ID = CLASS + "/get_by_id/{id}";
    String CLASS_VIEW = CLASS + "/view";
    String CLASS_FILTER = CLASS + "/filter";

    String CLASS_GET_ALL = CLASS + "/get_all";
    String CLASS_GET_DETAIL = CLASS + "/get_detail";
    String CLASS_GET_LOCATION = CLASS + "/location";
    String CLASS_CHANGE_TRAINING = CLASS + "/change-training-program";
    String CLASS_CHANGE_SYLLABUS = CLASS + "/change-syllabus-for-class";
}
