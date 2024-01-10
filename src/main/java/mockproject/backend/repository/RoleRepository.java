package mockproject.backend.repository;

import mockproject.backend.domain.entity.Role;
import mockproject.backend.domain.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(ERole role);
}