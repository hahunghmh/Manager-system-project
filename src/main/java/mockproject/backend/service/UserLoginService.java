/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/28/2023 - 8:21 AM
 */


package mockproject.backend.service;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.user.UserForMail;
import mockproject.backend.domain.dto.user.UserLoginForm;
import mockproject.backend.domain.dto.user.UserLoginResponse;
import mockproject.backend.domain.entity.RoleGroup;
import mockproject.backend.domain.entity.User;
import mockproject.backend.domain.entity.enums.EPermission;
import mockproject.backend.domain.entity.enums.ERole;
import mockproject.backend.domain.entity.other.UserTemporary;
import mockproject.backend.repository.UserRepository;
import mockproject.backend.repository.other.UserTemporaryRepository;
import mockproject.backend.service.mail.MailService;
import mockproject.backend.service.template.UserLoginInterface;
import mockproject.backend.utils.tools.PasswordGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static mockproject.backend.utils.constant.ErrorCode.*;

@Service
public class UserLoginService implements UserLoginInterface {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MailService mailService;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTemporaryRepository userTemporaryRepository;
    @Autowired
    private UserService userService;

    //7AM 22/11/2023
    @Override
    public ResponseData changePassword(UserLoginForm userLoginForm, HttpServletResponse response) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseData(5, EM05, null);
        }
        //need encrypt matches with FE
        String firstEnc= bCryptPasswordEncoder.encode(userLoginForm.getPassword());
        user.setPassword(firstEnc);
        //        update password, set First login = true;
        user.setFirstLogin(true);
        userRepository.save(user);
        return new ResponseData(0, EM00, new UserLoginResponse(user));
    }

    @Override
    public ResponseData login(String email, String password, HttpServletResponse response) {
        User user = userRepository.findByEmail(email);
        if (user == null ||
                !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return new ResponseData(5, EM05, null);
        }
        UserLoginResponse userLoginResponse = modelMapper.map(user, UserLoginResponse.class);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        List<RoleGroup> roleGroupList = user.getRole().getRoleGroupList();
        ERole role = user.getRole().getRole();
        System.err.println(role);
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));

        roleGroupList.forEach(roleGroup -> {
            if (roleGroup.isActive())
                grantedAuthorities.add(new SimpleGrantedAuthority(roleGroup.getUserPermission().getPermission().toString()));
        });

        TokenAuthenticationService.addAuthentication(response, user.getEmail(), grantedAuthorities);
        String authorizationString = response.getHeader("Authorization");
        userLoginResponse.setToken(authorizationString);


        List<EPermission> permissionList = new ArrayList<>();
        roleGroupList.forEach((roleGroup -> {
            if (roleGroup.isActive())
                permissionList.add(roleGroup.getUserPermission().getPermission());
        }));

        userLoginResponse.setPermissionList(permissionList);
        userLoginResponse.setRole(user.getRole().getRole());

        return user.isFirstLogin() ?
                new ResponseData(0, EM00, userLoginResponse) :
                new ResponseData(98, EM98, userLoginResponse);
    }

    @Override
    public UserTemporary save(UserTemporary userTemporary) {
        return userTemporaryRepository.save(userTemporary);
    }

    @Override
    public UserTemporary findByEmailAndCode(String email, String code) {
        return userTemporaryRepository.findByEmailAndCode(email, code);
    }

    @Override
    public void delete(String email) {
        if (userTemporaryRepository.findByEmail(email) != null)
            userTemporaryRepository.delete(userTemporaryRepository.findByEmail(email));
    }

    @Override
    public UserTemporary findByEmail(String email) {
        return userTemporaryRepository.findByEmail(email);
    }

    @Override
    public void renewPasswordToken(User user) {
        UserTemporary userTemporaryA = findByEmail(user.getEmail());
        UserTemporary userTemporary = new UserTemporary();
        userTemporary.setEmail(user.getEmail());
        userTemporary.setCode(PasswordGenerator.generateRamdonURL());
        userTemporary.setName(user.getName());

//        if user not found in temporary, create new, send new email
        if (userTemporaryA == null) {
            userTemporaryRepository.save(userTemporary);
            mailService.sendMailRenewPassword(userTemporary);
            return;
        }
//        if userTemporary is available check expire, if expired, remove and create new mail
        if (userTemporaryA.getExpireAt().isBefore(LocalDateTime.now())) {
            userTemporaryRepository.delete(userTemporaryA);
            userTemporaryRepository.save(userTemporary);
            mailService.sendMailRenewPassword(userTemporary);
        }
    }

    @Override
    public void renewPassword(String email, String code) {
        UserTemporary userTemporary = findByEmailAndCode(email, code);
//        if not found => invalid, return
        if (userTemporary == null) {
            return;
        }
//        if  found and expired -> remove
        if (userTemporary.getExpireAt().isBefore(LocalDateTime.now())) {
            userTemporaryRepository.delete(userTemporary);
            return;
        }
        // if available and not expire
        User user = userService.findByEmail(email);
        if (user == null) {
            return;
        }
        //        set new password,encrypt 2 time, matches with login FE
        String newPassword = PasswordGenerator.generatePassword(8, 12);
        String firstEnc= bCryptPasswordEncoder.encode(newPassword);

        user.setPassword(firstEnc);
        user.setFirstLogin(false);
        UserForMail newMail = new UserForMail(user.getName(), user.getEmail(), newPassword);
        mailService.sendMailToUser(newMail);
        userRepository.save(user);
    }
}