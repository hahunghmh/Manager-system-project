/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/30/2023 - 10:14 PM
 */


package mockproject.backend.service.filter;

import mockproject.backend.domain.entity.ClassRoom;
import mockproject.backend.domain.entity.ClassUser;
import mockproject.backend.domain.entity.enums.EClassStatus;
import mockproject.backend.domain.entity.enums.EClassTime;
import mockproject.backend.domain.entity.enums.EUserType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.List;

public class ClassRoomSpecifications {
    public static Specification<ClassRoom> searchByKey(String key) {
        return (root, query, criteriaBuilder) -> {
            Predicate namePredicate = criteriaBuilder.like(root.get("className"), "%" + key + "%");
            Predicate codePredicate = criteriaBuilder.like(root.get("classCode"), "%" + key + "%");
            return criteriaBuilder.or(namePredicate, codePredicate);
        };
    }

    public static Specification<ClassRoom> filterByLocation(String location) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("location"), "%" + location + "%"));
    }

    public static Specification<ClassRoom> filterByFsu(String fsu) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("fsu"), fsu));
    }


    public static Specification<ClassRoom> filterByAfterDateTime(LocalDate dateTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), dateTime);
    }

    public static Specification<ClassRoom> filterByBeforeDateTime(LocalDate dateTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), dateTime);
    }

    public static Specification<ClassRoom> filterByClassTime(List<EClassTime> classTime) {
        return (root, query, criteriaBuilder) ->
                root.get("classTime").in(classTime);
    }

    public static Specification<ClassRoom> filterByStatus(List<EClassStatus> classStatus) {
        return (root, query, criteriaBuilder) ->
                root.get("classStatus").in(classStatus);
    }

    public static Specification<ClassRoom> filterByAttendee(List<EUserType> classAttendee) {
        return (root, query, criteriaBuilder) -> {
            Join<ClassRoom, ClassUser> classUserJoin = root.join("classUserList", JoinType.LEFT);
            Predicate output = classUserJoin.get("userType").in(classAttendee);
            query.distinct(true);
            return criteriaBuilder.and(output);
        };
    }

    //???
    public static Specification<ClassRoom> filterByTrainer(String trainer) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("user"), trainer);
    }
}
