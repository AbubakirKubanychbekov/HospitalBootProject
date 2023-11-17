package boss.service;

import boss.entities.*;
import boss.exception.MyException;
import boss.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final HospitalRepo hospitalRepo;
    private final DepartmentRepo departmentRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;

    public List<Appointment> findAll(String time, Patient patient, Doctor doctor) {
        return appointmentRepo.findAll();
    }

    public Appointment findById(Long appointmentId) {
        return appointmentRepo.findById(appointmentId).orElseThrow(
                () -> new MyException("Appointment with id: " + appointmentId + "not found"));
    }

    public List<Appointment> getAppointmentByHospitalId(Long hospitalId) {
        System.out.println(hospitalId);
        return appointmentRepo.getAppointmentByHospitalId(hospitalId);
    }


    @Transactional
    public void saveAppointment(Long hospitalId, Long patientId, Long doctorId, Long departmentId, Appointment appointment) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(() -> new MyException("Hospital NOT found"));
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new MyException("Patient NOT found"));
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new MyException("Doctor NOT found"));
        Department department = departmentRepo.findById(departmentId).orElseThrow(() -> new MyException("Department NOT found"));
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDepartment(department);
        appointment.setHospital(hospital);
        hospital.getAppointments().add(appointment);
        appointmentRepo.save(appointment);
    }




    @Transactional
    public void update(Long appointmentId, Appointment newAppointment) {
        Appointment ap = findById(appointmentId);
        ap.setDateTime(newAppointment.getDateTime());
        ap.setPatient(newAppointment.getPatient());
        ap.setDoctor(newAppointment.getDoctor());
        ap.setDepartment(newAppointment.getDepartment());
        ap.setHospital(newAppointment.getHospital());
    }
}
