/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/26/2023 - 10:18 PM
 */

package mockproject.backend.utils.constant;

import static mockproject.backend.utils.constant.ApiUrl.API;

public interface UserUrlAPI {
    String USER = API + "/user";
    //        for management
    String USER_CREATE = USER + "/create";
    String USER_UPDATE = USER + "/update";
    String USER_DELETE = USER + "/delete";
    String USER_GET_BY_ID_EMAIL = USER + "/get_by_id_email";
    String USER_MANAGER = USER + "/manager";



    //        for login
    String USER_LOGIN = USER + "/login";
    String USER_CHANGE_PASSWORD = USER + "/change_password";
    String USER_LOGOUT = USER + "/logout";
    String USER_CREATE_PASSWORD = USER + "/forgot/{email}/{code}";
    String USER_RENEW_PASSWORD = USER + "/forgot";


    String USER_PERMISSION = USER + "/permission";
    String USER_PERMISSION_EDIT = USER + "/permission/edit";

//        ...
}
