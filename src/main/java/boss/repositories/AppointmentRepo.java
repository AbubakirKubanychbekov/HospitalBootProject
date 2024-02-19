package boss.repositories;

import boss.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {

    @Query("SELECT a FROM Appointment a WHERE a.hospital.id = :hospitalId")
    List<Appointment> getAppointmentByHospitalId(@Param("hospitalId") Long hospitalId);

}
