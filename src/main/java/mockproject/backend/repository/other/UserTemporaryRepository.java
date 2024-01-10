package mockproject.backend.repository.other;

import mockproject.backend.domain.entity.other.UserTemporary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTemporaryRepository extends JpaRepository<UserTemporary, Long> {
    UserTemporary findByEmailAndCode(String email, String code);
    UserTemporary findByEmail(String email);
}