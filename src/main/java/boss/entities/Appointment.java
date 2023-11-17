package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Appointment {
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

    private LocalDate dateTime;

    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Hospital hospital;


    public <E> List<Patient> getPatients() {
        return new ArrayList<>();
    }


}
