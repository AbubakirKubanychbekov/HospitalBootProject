package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "doctors")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String position;
    private String email;

    @ManyToOne
    private Hospital hospital;

    @ManyToMany
    private List<Department>departments;

    @ToString.Exclude
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Appointment>appointments;

}
