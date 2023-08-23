package boss.api;

import boss.entities.Department;
import boss.entities.Doctor;
import boss.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorApi {

    private final DoctorService doctorService;

  @GetMapping
    String findAll(Model model,
                   @RequestParam(name = "firstName",required = false)String firstName,
                   @RequestParam(name = "lastName",required = false)String lastName,
                   @RequestParam(name = "position",required = false)String position,
                   @RequestParam(name = "email",required = false)String email){
      model.addAttribute("firstName",firstName);
      model.addAttribute("lastName",lastName);
      model.addAttribute("position",position);
      model.addAttribute("email",email);
      return "doctors/findAll";
  }

  @GetMapping("/{hospitalId}")
  String findAllDepartmentsByHospitalId(Model model, @PathVariable Long hospitalId) {
    model.addAttribute("hospitalId", hospitalId);
    List<Doctor> doctors = doctorService.getDoctorsByHospitalId(hospitalId);
    System.out.println("doctors = " + doctors);
    model.addAttribute("doctors",doctors);
    return "doctors/findDoctorsByHospital";
  }

  @GetMapping("/create/{hospitalId}")
  String createDoctorByHospitalId(@PathVariable Long hospitalId, Model model) {
    model.addAttribute("hospitalId", hospitalId);
    model.addAttribute("newDoctor", new Doctor());
    return "doctors/saveDoctorByHospital";
  }

  @PostMapping("/save/{hospitalId}")
  String saveDepartmentByHospitalId(@PathVariable Long hospitalId,
                                    @ModelAttribute Doctor doctor) {
    doctorService.save(hospitalId, doctor);
    return "redirect:/doctors/"+hospitalId;
  }

  @GetMapping("/update/{doctorId}")
  String updatePage(@PathVariable Long doctorId,Model model){
    model.addAttribute("currentDoctor",doctorService.findById(doctorId));
    return "doctors/update-page";
  }

  @PostMapping("/edit/{doctorId}")
  String editDoctor(@ModelAttribute Doctor newDoctor,
                        @PathVariable Long doctorId){
    doctorService.update(doctorId,newDoctor);
    return "redirect:/doctors/"+doctorService.findById(doctorId)
            .getHospital().getId();
  }

  @GetMapping("/{hospitalId}/delete/{doctorID}")
  String deleteDepartment(@PathVariable Long doctorID,
                          @PathVariable Long hospitalId){
    doctorService.deleteById(doctorID);
    return "redirect:/doctors/"+hospitalId;
  }


}
