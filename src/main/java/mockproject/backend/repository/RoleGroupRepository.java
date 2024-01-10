package mockproject.backend.repository;

import mockproject.backend.domain.entity.Role;
import mockproject.backend.domain.entity.RoleGroup;
import mockproject.backend.domain.entity.UserPermission;
import mockproject.backend.domain.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {
    @Query("select r from RoleGroup r where r.role.role = :role order by r.id")
    List<RoleGroup> findAllRolePermission(@Param("role") ERole role);
    @Transactional
    @Modifying
    @Query("update RoleGroup r set r.isActive = false where r.role = :role")
    int updateDeactivePermissionByRole(@Param("role") Role role);
    @Transactional
    @Modifying
    @Query("update RoleGroup r set r.isActive = :isActive where r.role = :role and r.userPermission = :userPermission")
    int updateIsActiveByRoleAndUserPermission(@Param("isActive") boolean isActive, @Param("role") Role role, @Param("userPermission") UserPermission userPermission);
    @Transactional
    @Modifying
    @Query("delete from RoleGroup r where r.role = :role")
    int removeRolePermission(Role role);
}