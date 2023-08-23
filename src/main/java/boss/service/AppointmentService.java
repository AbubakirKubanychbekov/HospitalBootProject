package boss.service;

import boss.entities.Appointment;
import boss.entities.Department;
import boss.repositories.AppointmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;

    public List<Appointment> findAll(String time) {
        return appointmentRepo.findAll();
    }
}
