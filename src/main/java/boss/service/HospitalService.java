package boss.service;

import boss.entities.Hospital;
import boss.exception.MyException;
import boss.repositories.HospitalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepo hospitalRepo;

    public List<Hospital> findAll() {
        return hospitalRepo.findAll();
    }

    public void save(Hospital hospital) {
        hospitalRepo.save(hospital);
    }

    public Hospital findById(Long hospitalId) {
        return hospitalRepo.findById(hospitalId).orElseThrow(()-> new RuntimeException("Hospital with id: "+hospitalId+" not found"));
    }
@Transactional
    public void update(Long hospitalId, Hospital newHospital) {
        Hospital hospital = findById(hospitalId);
        if (hospital==null){
            throw new MyException("Hospital with id: "+hospitalId+"not found");
        }
        hospital.setName(newHospital.getName());
        hospital.setAddress(newHospital.getAddress());
        hospital.setImage(newHospital.getImage());
    }

    public void deleteById(Long hospitalId) {
        hospitalRepo.deleteById(findById(hospitalId).getId());
    }
}
