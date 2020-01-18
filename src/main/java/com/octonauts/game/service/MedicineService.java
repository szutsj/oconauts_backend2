package com.octonauts.game.service;

import com.octonauts.game.model.dto.CrewDTO;
import com.octonauts.game.model.dto.CrewMemberDTO;
import com.octonauts.game.model.dto.MedicineDTO;
import com.octonauts.game.model.dto.MedicineStockDTO;
import com.octonauts.game.model.entity.CrewMember;
import com.octonauts.game.model.entity.Medicine;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.enums.MedicineType;
import com.octonauts.game.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineService {

    private MedicineRepository medicineRepository;

    @Autowired
    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public List<Medicine> initMedicineStock(Octopod octopod) {
        List<Medicine> medicineStock = new ArrayList<>();
        for (MedicineType medicineType : MedicineType.values()) {
            Medicine medicine = new Medicine(medicineType, octopod);
            medicineRepository.save(medicine);
            medicineStock.add(medicine);
        }
        return medicineStock;
    }

    public MedicineStockDTO createMedicineList(Octopod octopod) {
        List<MedicineDTO> medicineDTOS = new ArrayList<>();
        List<Medicine>  medicines = medicineRepository.findAllByOctopodAndUsedIsFalse(octopod);
        for(Medicine medicine : medicines){
            medicineDTOS.add(createMedicineDTO(medicine));
        }
        return new MedicineStockDTO(medicineDTOS);
    }

    public MedicineDTO createMedicineDTO(Medicine medicine){
        MedicineDTO medicineDTO = new MedicineDTO();
        medicineDTO.setType(medicine.getType());
        return medicineDTO;
    }

    public int pointsPaidForMedicines(Octopod octopod) {
        if (medicineRepository.countPrices(octopod) == null) {
            return 0;
        }
        return medicineRepository.countPrices(octopod);
    }
}
