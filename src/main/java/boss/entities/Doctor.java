package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "doctor_gen")
    @SequenceGenerator(name = "doctor_gen",
            sequenceName = "doctor_seq",
            allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String position;
    private String email;

    @Column(length = 500)
    private String image;


    @ManyToOne
    private Hospital hospital;

    @ManyToMany
    private List<Department> departments;

    @ToString.Exclude
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    @Override
    public String toString() {
        return " Doctor" +
                "\n" + firstName +
                "\n" + lastName +
                "\n" + position +
                "\n" + email;
    }
}
