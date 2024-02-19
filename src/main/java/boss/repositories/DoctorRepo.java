package boss.repositories;


import boss.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {


    @Query("select d from Doctor d where d.hospital.id= :hospitalId")
    List<Doctor> getDoctorsByHospitalId(Long hospitalId);

    @Query("select d from Doctor d where d.id= :doctorId")
    Doctor findDoctorById(Long doctorId);

    @Query("select distinct d from Doctor d join d.departments dept where dept.id = :departmentId")
    List<Doctor> getDoctorsByDepartmentId(Long departmentId);

    @Query("select d from Doctor d where d.hospital.id= :hospitalId")
    List<Doctor> getAllDoctors(Long hospitalId);

}
