package boss.api;

import boss.entities.Appointment;
import boss.entities.Department;
import boss.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentApi {

    private final AppointmentService appointmentService;

    @GetMapping
    private String findAll(Model model,
                           @RequestParam(name = "time", required = false) String time){
        model.addAttribute("time", time);
        List<Appointment> all = appointmentService.findAll(time);
        model.addAttribute("appointments", all);
        return "appointments/findAll";
    }
}
