/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/30/2023 - 9:23 PM
 */


package mockproject.backend.service.filter;

import mockproject.backend.domain.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class UserSpecifications {
    public static Specification<User> filterByNameAndId(String key) {
        return (root, query, criteriaBuilder) -> {
            Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + key + "%");
            Predicate idPredicate = criteriaBuilder.like(root.get("userId").as(String.class), "%" + key + "%");
            Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + key + "%");
            return criteriaBuilder.or(namePredicate, idPredicate, emailPredicate);
        };
    }
}
