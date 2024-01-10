package mockproject.backend.repository.other;

import mockproject.backend.domain.entity.other.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, Short> {
}