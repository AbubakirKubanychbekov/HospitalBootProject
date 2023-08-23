package boss.repositories;

import boss.entities.Patient;
import boss.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepo extends JpaRepository<Patient,Long> {
    @Query("select p from Patient p where p.hospital.id= :hospitalId")
    List<Patient> getPatientsByHospitalId(Long hospitalId);

    @Query("select p from Patient p where p.firstName= :firstName and p.lastName= :lastName and p.phoneNumber= :phoneNumber and p.gender = :gender and p.email = :email")
    List<Patient> findAll(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phoneNumber") String phoneNumber, @Param("gender") Gender gender, @Param("email") String email);


}
