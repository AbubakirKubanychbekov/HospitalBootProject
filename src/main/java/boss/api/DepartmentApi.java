package boss.api;

import boss.entities.Department;
import boss.service.DepartmentService;
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
}
