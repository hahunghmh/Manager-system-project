/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.repository;

import mockproject.backend.domain.entity.Role;
import mockproject.backend.domain.entity.User;
import mockproject.backend.domain.entity.enums.ERole;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    long countByRole(Role role);

    User findByUserIdAndEmail(Long id, String email);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    User findByName(String name);

    @Lazy
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    User findByEmail(String email);

    <T> Page<T> findBy(Class<T> tClass, Pageable pageable);

    @Transactional
    @Query("select u.userId from User u where LOWER(u.userId) LIKE LOWER(CONCAT('%', :searchString, '%')) ")
    List<Long> getUserId(@Param("searchString") String keyword);

    @Transactional
    @Query("select u.name from User u where LOWER(u.name) LIKE LOWER(CONCAT('%', :searchString, '%')) ")
    List<String> getNameUser(@Param("searchString") String keyword);

    @Transactional
    @Query("select u.email from User u where LOWER(u.email) LIKE LOWER(CONCAT('%', :searchString, '%')) ")
    List<String> getEmail(@Param("searchString") String keyword);


    @Transactional
    @Modifying
    @Query("UPDATE User e SET e.role = :field1 WHERE e.userId = :id")
    void updateUserRoleById(@Param("id") long id, @Param("field1") ERole role);

    //    @Query
//    List<Long> listId(Long userId);
//Tai - create class
    @Query("SELECT u  FROM User u WHERE u.role.id = 2 ORDER BY u.name ASC")
    List<User> findAllUsersWithAdmin();

    @Query("SELECT  u  FROM User u WHERE u.role.id = 3 ORDER BY u.name ASC")
    List<User> findAllUsersWithTrainer();

    @Query("SELECT u FROM User u ORDER BY u.email ASC")
    List<User> findAllEmail();

    Page<User> findAll(Specification<User> spec, Pageable pageRequest);
}
