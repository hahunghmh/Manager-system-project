/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/28/2023 - 8:22 AM
 */

package mockproject.backend.service.template;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.user.UserLoginForm;
import mockproject.backend.domain.entity.User;
import mockproject.backend.domain.entity.other.UserTemporary;

import javax.servlet.http.HttpServletResponse;

public interface UserLoginInterface {
    //7AM 22/11/2023
    //7AM 22/11/2023
    ResponseData changePassword(UserLoginForm userLoginForm, HttpServletResponse response);

    ResponseData login(String email, String password, HttpServletResponse response);

    UserTemporary save(UserTemporary userTemporary);

    UserTemporary findByEmailAndCode(String email, String code);

    void delete(String email);

    UserTemporary findByEmail(String email);

    void renewPasswordToken(User user);

    void renewPassword(String email, String code);
}
