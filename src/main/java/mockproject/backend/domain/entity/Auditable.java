/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class Auditable {

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by", referencedColumnName = "user_id")
    protected User createdBy;

    @CreatedDate
    protected LocalDate creationDate;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modify_by", referencedColumnName = "user_id")
    protected User lastModifiedBy;

    @LastModifiedDate
    protected LocalDate lastModifiedDate;

    public User getCreatedBy() {
        if (createdBy == null) {
            User unknown = new User();
            unknown.setName("System");
            return unknown;
        } else {
            return createdBy;
        }
    }

    public User getLastModifiedBy() {
        if (lastModifiedBy == null) {
            User unknown = new User();
            unknown.setName("System");
            return unknown;
        } else {
            return lastModifiedBy;
        }
    }
}