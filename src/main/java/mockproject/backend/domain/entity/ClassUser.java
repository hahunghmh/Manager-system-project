/*
 *@project : BackEnd
 *@Create by: TrienND
 *@Create time: 11/16/2023 - 3:10 PM
 */


package mockproject.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockproject.backend.domain.entity.enums.EUserType;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class_user")
public class ClassUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EUserType userType;


    //mapping
    //    one class user to many user
    @OneToMany(mappedBy = "classUser")
    private List<User> userList;

    //    many class user to one class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id",referencedColumnName = "class_id")
    private ClassRoom tClassRoom;

    //    usertype??? admin? supper-admin ? trainer???


}
