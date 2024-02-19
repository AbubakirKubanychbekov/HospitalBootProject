package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "appointment_gen")
    @SequenceGenerator(name = "appointment_gen",
            sequenceName = "appointment_seq",
            allocationSize = 1)
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
