package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;


@Entity
@Table(name = "departments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_gen"
    )
    @SequenceGenerator(
            name = "user_gen",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    private String image;

    @ToString.Exclude
    @ManyToMany(mappedBy = "departments",cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    },fetch = FetchType.EAGER)
    private List<Doctor> doctors;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {REFRESH,MERGE,PERSIST,DETACH})
    private Hospital hospital;

}
