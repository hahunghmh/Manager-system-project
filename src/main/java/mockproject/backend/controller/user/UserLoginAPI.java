/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/26/2023 - 10:11 PM
 */


package mockproject.backend.controller.user;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.user.UserLoginForm;
import mockproject.backend.service.UserLoginService;
import mockproject.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static mockproject.backend.utils.constant.ErrorCode.EM00;
import static mockproject.backend.utils.constant.ErrorCode.EM99;
import static mockproject.backend.utils.constant.UserUrlAPI.*;

@RestController
@RequestMapping
public class UserLoginAPI {
    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService userLoginService;

   //    7:30 22/11/2023
    @PostMapping(USER_CHANGE_PASSWORD)
    ResponseEntity<Object> changePassword(@RequestBody UserLoginForm user, HttpServletResponse response) {
        ResponseData result = userLoginService.changePassword(user, response);
        return ResponseEntity.ok(result);
    }

    @PostMapping(USER_LOGIN)
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginForm user, BindingResult bindingResult, HttpServletResponse response) {
//        check valid
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }


        ResponseData result = userLoginService.login(user.getEmail(), user.getPassword(), response);

        return (result.getStatusCode() == 0 || result.getStatusCode() == 5||result.getStatusCode()==98) ?
                ResponseEntity.ok().body(result) :
                ResponseEntity.badRequest().body(result);
    }

    //test later
    @GetMapping(USER_LOGOUT)
    public ResponseEntity<ResponseData> logout(HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(new ResponseData(0, EM00, null));
    }

    //   ok
    @GetMapping(USER_RENEW_PASSWORD+"/{email}/{code}")
    public void renewPassword(@PathVariable String email, @PathVariable String code) {
        userLoginService.renewPassword(email, code);
    }

    @PostMapping(USER_RENEW_PASSWORD)
    public ResponseEntity<Object> renewPassword(@RequestBody UserLoginForm user) {
//        find and send email renew password
        if (userService.findByEmail(user.getEmail()) == null) {
            return ResponseEntity.ok(new ResponseData(-1, EM99, null));
        }
        userLoginService.renewPasswordToken(userService.findByEmail(user.getEmail()));
        return ResponseEntity.ok(new ResponseData(0, EM00, null));
    }

}
