package boss.entities;

import boss.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_gen"
    )
    @SequenceGenerator(
            name = "patient_gen",
            sequenceName = "patient_seq",
            allocationSize = 1
    )
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "^\\+996\\d+$", message = "Phone Number must start with +996")
    @Size(max = 13, message = "Phone number should not exceed 13 characters")
    @Column(name = "phone_number")
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    @NotNull(message = "Email column is empty")
    @Size(max = 100,message = "Email column size few character")
    private String email;

    @Column(length = 500)
    private String image;

    @ManyToOne
    private Hospital hospital;

    @ToString.Exclude
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    public Patient(@NotNull String firstName, @NotNull String lastName, String phoneNumber, Gender gender, @NotNull(message = "Email column is empty") String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;

    }

    @Override
    public String toString() {
        return " \nPatient " +
                "\n'" + firstName + '\'' +
                "\n'" + lastName + '\'' +
                "\n'" + phoneNumber + '\'' +
                "\n'" + gender +
                "\n'" + email + '\'' ;
    }
}
