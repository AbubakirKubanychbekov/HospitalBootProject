package boss.service;

import boss.entities.Department;
import boss.entities.Doctor;
import boss.entities.Hospital;
import boss.exception.MyException;
import boss.repositories.DoctorRepo;
import boss.repositories.HospitalRepo;
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
}
