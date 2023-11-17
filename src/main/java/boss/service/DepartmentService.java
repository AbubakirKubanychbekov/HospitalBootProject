package boss.service;

import boss.entities.Department;
import boss.entities.Doctor;
import boss.entities.Hospital;
import boss.exception.MyException;
import boss.repositories.DepartmentRepo;
import boss.repositories.DoctorRepo;
import boss.repositories.HospitalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final HospitalRepo hospitalRepo;
    private final DoctorRepo doctorRepo;


    public List<Department>findAll(){
        return departmentRepo.findAll();
    }
    public List<Department>findAll(String name){
        return departmentRepo.findAll();
    }

    public List<Department> getDepartmentsByHospitalId(Long hospitalId) {
       return departmentRepo.getDepartmentsByHospitalId(hospitalId);

    }


    public void save(Long hospitalId, Department department) {

        Hospital hospital = hospitalRepo.findById(hospitalId)
                .orElseThrow(()-> new RuntimeException("Hospital by Id null"));

        hospital.addDepartment(department);

        department.setHospital(hospital);

        departmentRepo.save(department);

//        if (hospital.getDepartments() != null){
//           hospital.getDepartments().add(department);
//       }else {
//            hospital.setDepartments(new ArrayList<>(List.of(department)));
//        }
         hospitalRepo.save(hospital);

    }

    public Department findById(Long departmentId){
      return departmentRepo.findById(departmentId).orElseThrow(()->
              new RuntimeException("Department with id "+departmentId+"not found"));
    }


    @Transactional
    public void update(Long departmentId, Department newDepartment) {
        Department dep = findById(departmentId);
        dep.setName(newDepartment.getName());
        dep.setImage(newDepartment.getImage());
    }

    public void deleteById(Long departmentId) {
        Department department = departmentRepo.findById(departmentId).orElseThrow(()
                -> new MyException("Department with id:" + departmentId + "not found!"));
        Hospital hospital = department.getHospital();
        hospital.getDepartments().remove(department);
        departmentRepo.deleteById(departmentId);
    }

    @Transactional
    public void assign(Long departmentId,Long doctorId){
        Department dep = departmentRepo.findById(departmentId).orElse(null);
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);
        dep.getDoctors().add(doctor);
        doctor.getDepartments().add(dep);
    }

    public List<Doctor> getAllDoctorFromDepartment(Long departmentId) {
        Department dep = findById(departmentId);
        List<Doctor> doctors = dep.getDoctors();
        dep.setDoctors(doctors);
        return dep.getDoctors();
    }
}
