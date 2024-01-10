package mockproject.backend.repository;

import mockproject.backend.domain.entity.ClassRoom;
import mockproject.backend.domain.entity.ClassUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Hà Mạnh Hùng
 * @mailto : hahunghmh@gmail.com
 * @created : 11/27/2023, Monday
 **/
public interface ClassUserRepository extends JpaRepository<ClassUser, Long> {

}
