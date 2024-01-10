/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */


package mockproject.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.enums.EGender;
import mockproject.backend.domain.entity.enums.EUserStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean isFirstLogin;
    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private EUserStatus status;

    @Enumerated(EnumType.STRING)
    private EGender gender;

//    many user map to one role

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    //    one user to many syllabus
    @OneToMany(mappedBy = "user")
    private List<Syllabus> syllabusList;

    //    one user to many training program
    @OneToMany(mappedBy = "user")
    private List<TrainingProgram> trainingProgramList;

    //    many user to one class user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_user_id", referencedColumnName = "class_user_id")
    private ClassUser classUser;

    //    auditor
    @OneToMany(mappedBy = "createdBy")
    private List<User> userListCreate;

    @OneToMany(mappedBy = "lastModifiedBy")
    private List<User> userListModify;
}
