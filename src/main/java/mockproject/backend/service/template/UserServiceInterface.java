/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/19/2023 - 1:39 PM
 */


package mockproject.backend.service.template;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.user.UserForCreate;
import mockproject.backend.domain.dto.user.UserForUpdate;
import mockproject.backend.domain.dto.user.UserForView;
import mockproject.backend.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserServiceInterface {
    Page<User> findByKeys(List<String> keys, Pageable pageRequest);

    //29/11/23
//for create class
    List<UserForView> getAdmin();

    List<UserForView> getTrainer();

    List<UserForView> getEmail();

//    ResponseData updateUser(UserForUpdate user);

    ResponseData getUserByIdEmail(UserForView user);

    //    7:30PM 23/11/2023
    ResponseData updateUser(UserForUpdate userForUpdate, HttpServletResponse response);

    ResponseData deleteUserByIdEmail(UserForUpdate user);

    //7AM 22/11/2023
    ResponseData create(UserForCreate user);

    User findByEmail(String email);

}
