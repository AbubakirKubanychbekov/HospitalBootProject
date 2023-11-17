package boss.api;

import boss.entities.Hospital;
import boss.service.HospitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hospitals")
public class HospitalApi {
  private final HospitalService hospitalService;

    public HospitalApi(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping()
    String findAll(Model model){
        List<Hospital> allHospital = hospitalService.findAll();
        model.addAttribute("hospitals",allHospital);
        return "findAll";
    }
@GetMapping("/create")
    String createHospital(Model model){
        model.addAttribute("newHospital",new Hospital());
        return "savePage";
}

   @PostMapping("/save")
        String saveHospital(@ModelAttribute("newHospital") Hospital hospital){
        hospitalService.save(hospital);
        return "redirect:/hospitals";
}



   @GetMapping("/update/{hospitalId}")
    String updatePage(@PathVariable Long hospitalId,Model model){
        model.addAttribute("currentHospital",hospitalService.findById(hospitalId));
        return "updatePage";
}

   @PostMapping("/edit/{hospitalId}")
    String editHospital(@ModelAttribute Hospital newHospital,
                        @PathVariable Long hospitalId){
        hospitalService.update(hospitalId,newHospital);
        return "redirect:/hospitals";
}

   @GetMapping("/delete/{hospitalId}")
  String deleteHospital(@PathVariable Long hospitalId){
        hospitalService.deleteById(hospitalId);
        return "redirect:/hospitals";
   }

   @GetMapping("/search")
    public String searchHospital(Model model,
                                 @RequestParam (required = false) String name,
                                 @RequestParam (required = false) String address){
        model.addAttribute("name",name);
        model.addAttribute("address",address);
        model.addAttribute("hospitals",hospitalService.getAllHospitalList(name,address));
        return "findAll";
   }
}
