/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 12/3/2023 - 10:42 PM
 */


package mockproject.backend.service.filter;

import mockproject.backend.domain.entity.TrainingProgram;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class TrainingProgramSpecifications {
    public static Specification<TrainingProgram> searchByKey(String key) {
        return (root, query, criteriaBuilder) -> {
            Predicate trainingProgramCode = criteriaBuilder.like(root.get("trainingProgramCode").as(String.class), "%" + key + "%");
            Predicate name = criteriaBuilder.like(root.get("name"), "%" + key + "%");
            return criteriaBuilder.or(name, trainingProgramCode);
        };
    }
}
