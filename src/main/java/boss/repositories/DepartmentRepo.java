package boss.repositories;

import boss.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department,Long> {


    @Query("select d from Department d where d.hospital.id = :hospitalId")
    List<Department> getDepartmentsByHospitalId(Long hospitalId);

}
