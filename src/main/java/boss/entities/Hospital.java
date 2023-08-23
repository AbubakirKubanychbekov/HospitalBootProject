package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "hospitals")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {
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

    private String image;
    private String name;

    private String address;

    @OneToMany(mappedBy = "hospital")
    private List<Doctor>doctors;

    @ToString.Exclude
    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Patient>patients;

    @OneToMany(mappedBy = "hospital",cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    },fetch = FetchType.EAGER)
    private List<Department>departments;

    @OneToMany
    private List<Appointment>appointments;

    public void addDepartment(Department department) {
        if (departments == null){
            departments = new ArrayList<>();
        } else {
            departments.add(department);
        }
    }

    public void addPatient(Patient patient) {
      if (patients == null){
         patients=new ArrayList<>();
      } else {
         patients.add(patient);
      }
    }

    public void addDoctor(Doctor doctor) {
       if (doctors == null){
           doctors=new ArrayList<>();
       }else {
           doctors.add(doctor);
       }
    }
}
