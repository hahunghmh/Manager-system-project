/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */


package mockproject.backend.service.auditor;

import mockproject.backend.domain.entity.User;
import mockproject.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorAwareImpl implements AuditorAware<User> {
    @Autowired
    private UserService userService;

    @Override
    public Optional<User> getCurrentAuditor() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(userService.findByEmail(email));
    }
}
