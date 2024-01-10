/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/30/2023 - 10:26 PM
 */


package mockproject.backend.domain.entity.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    private String name;
    private String code;
    @ManyToOne
    @JoinColumn(name = "fsu_id", referencedColumnName = "id")
    private FSU fsu;
}
