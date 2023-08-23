package boss.repositories;


import boss.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {


    @Query("select d from Doctor d where d.hospital.id= :hospitalId")
    List<Doctor> getDoctorsByHospitalId(Long hospitalId);
}
