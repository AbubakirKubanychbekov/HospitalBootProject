package boss.repositories;

import boss.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AppointmentRepo extends JpaRepository<Appointment,Long> {

    @Query("SELECT a FROM Appointment a WHERE a.hospital.id = :hospitalId")
    List<Appointment> getAppointmentByHospitalId(@Param("hospitalId") Long hospitalId);

}
