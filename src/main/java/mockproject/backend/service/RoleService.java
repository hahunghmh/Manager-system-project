/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/20/2023 - 7:24 PM
 */


package mockproject.backend.service;

import mockproject.backend.domain.entity.Role;
import mockproject.backend.domain.entity.enums.ERole;
import mockproject.backend.repository.RoleRepository;
import mockproject.backend.service.template.RoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleServiceInterface {
    @Autowired
    private RoleRepository roleRepository;

    Role findByID(Long id) {
        return roleRepository.findById(id).get();
    }

    Role findByERole(ERole eRole) {
        return roleRepository.findByRole(eRole);
    }

}

