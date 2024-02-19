package boss.api;

import boss.entities.Department;
import boss.entities.Doctor;
import boss.service.DepartmentService;
import boss.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentApi {

    private final DepartmentService departmentService;
    private final DoctorService doctorService;


    @GetMapping
    private String findAll(Model model,
                           @RequestParam(name = "name", required = false) String name){
        model.addAttribute("name", name);
        List<Department> all = departmentService.findAll(name);
        model.addAttribute("departments", all);
        return "departments/findAll";
    }



    @GetMapping("/{hospitalId}")
    String findAllDepartmentsByHospitalId(Model model, @PathVariable Long hospitalId) {
        model.addAttribute("hospitalId", hospitalId);
        List<Department> departments = departmentService.getDepartmentsByHospitalId(hospitalId);
        System.out.println("departments = " + departments);
        model.addAttribute("departments",departments );
        return "departments/findDepartmentsByHospital";
    }

    @GetMapping("/create/{hospitalId}")
    String createDepartmentByHospitalId(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("newDepartment", new Department());
        return "departments/saveDepartmentByHospital";
    }

    @PostMapping("/save/{hospitalId}")
    String saveDepartmentByHospitalId(@PathVariable Long hospitalId,
                                      @ModelAttribute Department department) {
        departmentService.save(hospitalId, department);
        return "redirect:/departments/"+hospitalId;
    }

    //findDepartmentsByHospital

    @GetMapping("/update/{departmentId}")
    String updatePage(@PathVariable Long departmentId,Model model){
        model.addAttribute("currentDepartment",departmentService.findById(departmentId));
        return "departments/update-page";
    }

    @PostMapping("/edit/{departmentId}")
    String editDepartment(@ModelAttribute Department newDepartment,
                          @PathVariable Long departmentId){
        departmentService.update(departmentId,newDepartment);
        return "redirect:/departments/"+departmentService.findById(departmentId)
                .getHospital().getId();
    }

    @GetMapping("/{hospitalId}/delete/{departmentId}")
    String deleteDepartment(@PathVariable Long departmentId,
                            @PathVariable Long hospitalId){
        departmentService.deleteById(departmentId);
        return "redirect:/departments/"+hospitalId;
    }

    @GetMapping("/assign/{departmentId}")
    public String assign(@PathVariable Long departmentId, Model model) {
        model.addAttribute("doctors",doctorService.findAll());
        model.addAttribute("departmentId",departmentId);
        return "departments/assignDoctor";
    }

    @PostMapping("/accept/{departmentId}")
    public String acceptAssign(@PathVariable Long departmentId,
                               Long doctorId){
        departmentService.assign(departmentId, doctorId);
        return "redirect:/departments/"+departmentId;
    }

    @GetMapping("/doctorInDepartment/{departmentId}")
    public String getDoctorFromDepartment(Model model,
                                          @PathVariable Long hospitalId,
                                          @PathVariable ("departmentId") Long departmentId) {
        model.addAttribute("allDoctor", departmentService.getAllDoctorFromDepartment(departmentId));
        model.addAttribute("hospitalIds", hospitalId);
        return "departments/doctorsAll";
    }


    @GetMapping("/{hospitalId}/doctors/{departmentId}")
    public String getDoctorsInDepartment(@PathVariable Long hospitalId,@PathVariable Long departmentId, Model model) {
        List<Doctor> doctors = doctorService.getDoctorsByDepartmentId(departmentId);
        model.addAttribute("doctors", doctors);
        return "departments/doctors_in_department";
    }
}
