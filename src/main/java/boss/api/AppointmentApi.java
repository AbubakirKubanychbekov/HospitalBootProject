package boss.api;

import boss.entities.Appointment;
import boss.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments/{hospitalId}")
@RequiredArgsConstructor
public class AppointmentApi {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final HospitalService hospitalService;


    @GetMapping
    String findAllDepartmentsByHospitalId(Model model, @PathVariable Long hospitalId) {
        System.out.println("get all " + hospitalId);
        model.addAttribute("hospitalId", hospitalId);
        List<Appointment> appointments = appointmentService.getAppointmentByHospitalId(hospitalId);
//        System.out.println("appointments = " + appointments);
        model.addAttribute("appointments",appointments);
        return "appointments/findAppointmentsByHospital";
    }

    @GetMapping("/create")
    String createAppointmentByHospitalId(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("patients",patientService.findAll());
        model.addAttribute("doctors",doctorService.findAll());
        model.addAttribute("departments",departmentService.findAll());
        model.addAttribute("hospitals",hospitalService.findAll());
        return "appointments/saveAppointmentByHospital";
    }

    @PostMapping("/save")
    String saveAppointmentByHospitalId(@PathVariable Long hospitalId,
                                     @RequestParam Long patientId,
                                     @RequestParam Long doctorId,
                                     @RequestParam Long departmentId,
                                       @ModelAttribute Appointment appointment) {
        appointmentService.saveAppointment(hospitalId, patientId, doctorId, departmentId, appointment);
        return "redirect:/appointments/"+hospitalId;
    }

    @GetMapping("/update/{appointmentId}")
    String updatePage(@PathVariable Long hospitalId,
            @PathVariable Long appointmentId,
                      Model model){
        model.addAttribute("currentAppointment",appointmentService.findById(appointmentId));
        return "appointments/update-page";
    }

    @PostMapping("/edit/{appointmentId}")
    String editAppointment(@PathVariable Long hospitalId,
            @ModelAttribute Appointment newAppointment,
                      @PathVariable Long appointmentId){
        appointmentService.update(appointmentId,newAppointment);
        return "redirect:/appointments/"+appointmentId;
    }

}
