/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/19/2023 - 9:58 AM
 */


package mockproject.backend.controller.user;

import mockproject.backend.domain.dto.*;
import mockproject.backend.domain.dto.user.UserForCreate;
import mockproject.backend.domain.dto.user.UserForUpdate;
import mockproject.backend.domain.dto.user.UserForView;
import mockproject.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static mockproject.backend.utils.constant.ErrorCode.EM00;
import static mockproject.backend.utils.constant.UserUrlAPI.*;

@RestController
public class UserAPI {
    @Autowired
    private UserService userService;

    //    7:30PM
    @PostMapping(USER_UPDATE)
    public ResponseEntity<Object> postUpdateUser(@RequestBody UserForUpdate user,HttpServletResponse response) {
        ResponseData responseData = userService.updateUser(user,response);
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping(USER_DELETE)
    public ResponseEntity<Object> postDeleteUser(@RequestBody UserForUpdate user) {
        ResponseData responseData = userService.deleteUserByIdEmail(user);
        return ResponseEntity.ok().body(responseData);
    }

    @PostMapping(USER_GET_BY_ID_EMAIL)
    private ResponseEntity<Object> getUserByIdEmail(@RequestBody UserForView user) {
        return ResponseEntity.ok(userService.getUserByIdEmail(user));
    }


    //    create user
    @PostMapping(USER_CREATE)
    public ResponseEntity<Object> createUser(@RequestBody UserForCreate user, HttpServletResponse response) {
        ResponseData result = userService.create(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping(USER_MANAGER)
    public ResponseEntity<ResponseData> getUserManager(@RequestBody PageConfigVer2 config) {

        Page<UserForView> result = userService.findByKeys(config.getKeyword(), config.getPageableConfig()).map(UserForView::new);
        ResponseData resultRes = new ResponseData(0, EM00, result);

        return ResponseEntity.ok(resultRes);
    }
}
