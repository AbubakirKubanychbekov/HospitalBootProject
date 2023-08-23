package boss.service;

import boss.entities.Hospital;
import boss.entities.Patient;
import boss.enums.Gender;
import boss.exception.MyException;
import boss.repositories.HospitalRepo;
import boss.repositories.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepo patientRepo;
    private final HospitalRepo hospitalRepo;



    public List<Patient> findAll(String firstName, String lastName, String phoneNumber, Gender gender, String email) {
      return patientRepo.findAll(firstName,lastName,phoneNumber,gender,email);

    }



    public List<Patient> getPatientsByHospitalId(Long hospitalId) {
        return patientRepo.getPatientsByHospitalId(hospitalId);
    }

    public void save(Long hospitalId, Patient patient) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElse(null);
        hospital.addPatient(patient);
        patient.setHospital(hospital);
        patientRepo.save(patient);

        if (hospital.getPatients() !=null){
            hospital.getPatients().add(patient);
        }else {
            hospital.setPatients(new ArrayList<>(List.of(patient)));
        }
       hospitalRepo.save(hospital);
    }

    public Patient findById(Long patientId){
        return patientRepo.findById(patientId).orElseThrow(()-> new RuntimeException("Patient with id: "+patientId+"not found..."));
    }

    public void update(Long patientId, Patient newPatient) {
        Patient pat = findById(patientId);
        pat.setFirstName(newPatient.getFirstName());
        pat.setLastName(newPatient.getLastName());
        pat.setPhoneNumber(newPatient.getPhoneNumber());
        pat.setGender(newPatient.getGender());
        pat.setEmail(newPatient.getEmail());
    }

    public void deleteById(Long patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(
                () -> new MyException("Patient with id: " + patientId + " not found!"));
        Hospital hospital = patient.getHospital();
        hospital.getPatients().remove(patient);
        patientRepo.deleteById(patientId);

    }
}
