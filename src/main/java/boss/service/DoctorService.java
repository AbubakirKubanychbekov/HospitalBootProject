package boss.service;

import boss.entities.Doctor;
import boss.entities.Hospital;
import boss.exception.MyException;
import boss.repositories.DoctorRepo;
import boss.repositories.HospitalRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final HospitalRepo hospitalRepo;

    @PersistenceContext
    private EntityManager entityManager;


    public List<Doctor> getDoctorsByHospitalId(Long hospitalId) {
        return doctorRepo.getDoctorsByHospitalId(hospitalId);
    }

    public void save(Long hospitalId, Doctor doctor) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(
                () -> new MyException("Doctor with id: " + hospitalId + " not found!"));
        hospital.addDoctor(doctor);
        doctor.setHospital(hospital);
        doctorRepo.save(doctor);
        if (hospital.getDoctors() != null){
            hospital.getDoctors().add(doctor);
        }else {
            hospital.setDoctors(new ArrayList<>(List.of(doctor)));
        }
        hospitalRepo.save(hospital);
    }


    public Doctor findById(Long doctorId) {
        return doctorRepo.findById(doctorId).orElseThrow(()-> new MyException("Doctor with id :"+doctorId+" not found!"));

    }

    public void update(Long doctorId, Doctor newDoctor) {
        Doctor doctor = findById(doctorId);
        doctor.setFirstName(newDoctor.getFirstName());
        doctor.setLastName(newDoctor.getLastName());
        doctor.setPosition(newDoctor.getPosition());
        doctor.setEmail(newDoctor.getEmail());

    }

    public void deleteById(Long doctorID) {
        Doctor doctor = doctorRepo.findById(doctorID).orElseThrow(() ->
                new MyException("doctor with id: " + doctorID + "not found!"));
        Hospital hospital = doctor.getHospital();
        hospital.getDoctors().remove(doctor);
        doctorRepo.deleteById(doctorID);
    }

    public List<Doctor> getAllDoctors(Long hospitalId) {
        return doctorRepo.getAllDoctors(hospitalId);
    }

    public List<Doctor> findAll() {
        return doctorRepo.findAll();
    }

    public List<Doctor> getDoctorsByDepartmentId(Long departmentId) {
        return doctorRepo.getDoctorsByDepartmentId(departmentId);
    }

    public List<Doctor> searchDoctorByNameAndSurname(String firstName, String lastName) {
        return entityManager.createQuery("select d from Doctor d where d.firstName= :firstName or d.lastName=: lastName", Doctor.class)
                .setParameter("firstName",firstName).setParameter("lastName",lastName)
                .getResultList();
    }
}
