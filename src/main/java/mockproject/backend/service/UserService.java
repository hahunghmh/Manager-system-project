/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/19/2023 - 1:25 PM
 */


package mockproject.backend.service;

import mockproject.backend.domain.dto.ResponseData;
import mockproject.backend.domain.dto.user.UserForCreate;
import mockproject.backend.domain.dto.user.UserForMail;
import mockproject.backend.domain.dto.user.UserForUpdate;
import mockproject.backend.domain.dto.user.UserForView;
import mockproject.backend.domain.entity.Role;
import mockproject.backend.domain.entity.User;
import mockproject.backend.domain.entity.enums.EPermission;
import mockproject.backend.domain.entity.enums.ERole;
import mockproject.backend.repository.UserRepository;
import mockproject.backend.service.filter.UserSpecifications;
import mockproject.backend.service.mail.MailService;
import mockproject.backend.service.template.UserServiceInterface;
import mockproject.backend.utils.tools.PasswordGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mockproject.backend.utils.constant.ErrorCode.*;

@Service
public class UserService implements UserServiceInterface {
    //    important!!!!
//update && delete need to be checked count number of super admin first
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private RoleService roleService;

    //impl filter
    @Override
    public Page<User> findByKeys(List<String> keys, Pageable pageRequest) {
        Specification<User> spec = Specification.where(null);
        if (Objects.nonNull(keys) && !keys.isEmpty()) {
            for (String key : keys) {
                spec = spec.and(UserSpecifications.filterByNameAndId(key));
            }
        }
        return userRepository.findAll(spec, pageRequest);
    }


    //29/11/23
    //for create class
    @Override
    public List<UserForView> getAdmin() {
        List<UserForView> result = new ArrayList<>();
        userRepository.findAllUsersWithAdmin().forEach(user -> result.add(new UserForView(user)));
        return result;
    }

    @Override
    public List<UserForView> getTrainer() {
        List<UserForView> result = new ArrayList<>();
        userRepository.findAllUsersWithTrainer().forEach(user -> result.add(new UserForView(user)));
        return result;
    }

    @Override
    public List<UserForView> getEmail() {
        List<UserForView> result = new ArrayList<>();
        userRepository.findAllEmail().forEach(user -> result.add(new UserForView(user)));
        return result;
    }

    //    7:30PM 23/11/2023
    @Override
    public ResponseData updateUser(UserForUpdate userForUpdate, HttpServletResponse response) {
        if (!checkPermission(EPermission.UPDATE_USER.toString())) {
            return new ResponseData(-1, EMNEV, null);
        }

        User newUser = userRepository.findByUserIdAndEmail(userForUpdate.getUserId(), userForUpdate.getEmail());
        if (newUser == null) {
            return new ResponseData(1, EM52, null);
        }
        newUser.setName(userForUpdate.getName());
//        newUser.setEmail(userForUpdate.getEmail());
        newUser.setPhone(userForUpdate.getPhone());
        newUser.setDob(userForUpdate.getDob());
        newUser.setGender(userForUpdate.getGender());
        newUser.setStatus(userForUpdate.getStatus());

//update && delete need to be checked count number of super admin first
        if (ERole.SUPERADMIN.equals(newUser.getRole().getRole())) {
            long numberSuperAdmin = userRepository.countByRole(newUser.getRole());
//            if number of SA ==1 && userForUpdate have role different supper-admin => return bad request
            if (numberSuperAdmin <= 1 &&
                    (!ERole.SUPERADMIN.equals(userForUpdate.getRole())
                    ))
                return new ResponseData(-1, EMNEV, null);
        }

        Role role = roleService.findByERole(userForUpdate.getRole());
        newUser.setRole(role);
        userRepository.save(newUser);
        return new ResponseData(0, EM10, new UserForUpdate(newUser));
    }

    @Override
    public ResponseData deleteUserByIdEmail(UserForUpdate user) {
        if (!checkPermission(EPermission.DELETE_USER.toString())) {
            return new ResponseData(-1, EMNEV, null);
        }

        User userDelete = userRepository.findByUserIdAndEmail(user.getUserId(), user.getEmail());
        if (userDelete == null) {
            return new ResponseData(1, EM52, null);
        }

        //update && delete need to be checked count number of super admin first
        if (ERole.SUPERADMIN.equals(userDelete.getRole().getRole())) {
            long numberSuperAdmin = userRepository.countByRole(userDelete.getRole());
//            if number of SA ==1 && userForUpdate have role = supper-admin => return bad request
//            or any user
            if (numberSuperAdmin <= 1 && (ERole.SUPERADMIN.equals(user.getRole())))
                return new ResponseData(-1, EMNEV, null);
        }

        try {
            userRepository.delete(userDelete);
        } catch (Exception e) {
            return new ResponseData(-1, EMNEV, null);
        }
        return new ResponseData(0, EM54, modelMapper.map(userDelete, UserForUpdate.class));
    }

    //    => ok
    @Override
    public ResponseData getUserByIdEmail(UserForView user) {
        User userUpdate = userRepository.findByUserIdAndEmail(user.getUserId(), user.getEmail());
        if (userUpdate == null) {
            return new ResponseData(1, EM52, null);
        }
        return new ResponseData(0, EM10, modelMapper.map(userUpdate, UserForView.class));
    }


    @Override
    public ResponseData create(UserForCreate user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseData(4, EM04, null);
        }
        //else create new user
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setDob(user.getDob());
        newUser.setGender(user.getGender());
        newUser.setStatus(user.getStatus());
        String generatePassword = PasswordGenerator.generatePassword(8, 12);
        newUser.setPassword(bCryptPasswordEncoder.encode(generatePassword));

        Role role = roleService.findByERole(user.getRole());

        newUser.setRole(role);
        userRepository.save(newUser);

        //send email to user
        UserForMail newMail = new UserForMail(user.getName(), user.getEmail(), generatePassword);
        mailService.sendMailToUser(newMail);
        return new ResponseData(0, EM09, null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private boolean checkPermission(String permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (permission.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
