package boss.api;

import boss.entities.Patient;
import boss.enums.Gender;
import boss.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientApi {

    private final PatientService patientService;

    @GetMapping
    String findAll(Model model,
                   @RequestParam(name = "firstName",required = false)String firstName,
                   @RequestParam(name = "lastName",required = false)String lastName,
                   @RequestParam(name = "phoneNumber",required = false)String phoneNumber,
                   @RequestParam(name = "gender",required = false)Gender gender,
                   @RequestParam(name = "email",required = false)String email){
        model.addAttribute("firstName",firstName);
        model.addAttribute("lastName",lastName);
        model.addAttribute("phoneNumber",phoneNumber);
        model.addAttribute("gender",gender);
        model.addAttribute("email",email);
       List<Patient> patients= patientService.findAll(firstName,lastName,phoneNumber,gender,email);
        model.addAttribute("patients",patients);
        return "patients/findAll";
    }

    @GetMapping("/{hospitalId}")
    String findAllPatientsByHospitalId(Model model,@PathVariable Long hospitalId){
        System.out.println("hello3");
        model.addAttribute("hospitalId",hospitalId);
        model.addAttribute("patients",patientService.getPatientsByHospitalId(hospitalId));
        return "patients/findPatientsByHospital";
    }


    @GetMapping("/create/{hospitalId}")
    String createPatientByHospitalId(@PathVariable Long hospitalId,Model model){
        System.out.println("hello1");
        model.addAttribute("hospitalId",hospitalId);
        model.addAttribute("newPatient",new Patient());
        return "patients/savePatientByHospital";
    }

    @PostMapping("/save/{hospitalId}")
    String savePatientByHospitalId(@PathVariable Long hospitalId,
                                   @ModelAttribute Patient patient){
        System.out.println("hello2");
        patientService.save(hospitalId,patient);
        return "redirect:/patients/"+hospitalId;
    }

    @GetMapping("/update/{patientId}")
    String updatePage(@PathVariable Long patientId,Model model){
        model.addAttribute("currentPatient",patientService.findById(patientId));
        return "patients/update-page";
    }

    @PostMapping("/edit/{patientId}")
    String editPatient(@ModelAttribute Patient newPatient,
                       @PathVariable Long patientId){
        patientService.update(patientId,newPatient);
        return "redirect:/patients/"+patientService.findById(patientId)
                .getHospital().getId();
    }

    @GetMapping("/{hospitalId}/delete/{patientId}")
    String deletePatient(@PathVariable Long patientId,
                         @PathVariable Long hospitalId){
        patientService.deleteById(patientId);
        return "redirect:/patients/"+hospitalId;
    }

}
