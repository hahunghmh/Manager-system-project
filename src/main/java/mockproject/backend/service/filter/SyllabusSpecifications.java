/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 12/3/2023 - 11:15 PM
 */


package mockproject.backend.service.filter;

import mockproject.backend.domain.entity.LearningObjective;
import mockproject.backend.domain.entity.Syllabus;
import mockproject.backend.domain.entity.TrainingContent;
import mockproject.backend.domain.entity.TrainingUnit;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public class SyllabusSpecifications {
    public static Specification<Syllabus> searchByKey(String key) {
        return (root, query, criteriaBuilder) -> {
            Predicate createOn = criteriaBuilder.like(root.get("creationDate"), "%" + key + "%");
            Predicate topicName = criteriaBuilder.like(root.get("topicName"), "%" + key + "%");
            Predicate topicCode = criteriaBuilder.like(root.get("topicCode"), "%" + key + "%");

            // Find output standard
            Join<Syllabus, TrainingUnit> tuJoin = root.join("trainingUnitList", JoinType.LEFT);
            Join<TrainingUnit, TrainingContent> tcJoin = tuJoin.join("trainingContentList", JoinType.LEFT);
            Join<TrainingContent, LearningObjective> loJoin = tcJoin.join("learningObjectiveSet", JoinType.LEFT);

            Predicate outputStandard = criteriaBuilder.like(loJoin.get("type"), "%" + key + "%");
            query.distinct(true);
            return criteriaBuilder.or(outputStandard, topicName, topicCode);
        };
    }

    public static Specification<Syllabus> filterByCreatedDate(LocalDate start, LocalDate end) {
        return ((root, query, criteriaBuilder) ->
        {
            Predicate startDate = criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), start);
            if (end != null) {
                Predicate endDate = criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), end);
                return criteriaBuilder.and(startDate, endDate);
            } else return criteriaBuilder.and(startDate);
        });
    }
}
