package com.octonauts.game.service;

import com.octonauts.game.contsants.MedicinePrices;
import com.octonauts.game.model.dto.*;
import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.model.entity.cureFactory.Cure;
import com.octonauts.game.model.entity.medicineFactory.Medicine;
import com.octonauts.game.model.enums.MedicineType;
import com.octonauts.game.repository.OctopodRepository;
import com.octonauts.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OctopodService {

    private OctopodRepository octopodRepository;
    private UserRepository userRepository;
    private CrewService crewService;
    private MedicineService medicineService;
    private GupService gupService;
    private SicknessService sicknessService;
    private AnimalService animalService;

    @Autowired
    public OctopodService(OctopodRepository octopodRepository, UserRepository userRepository,
                          CrewService crewService, MedicineService medicineService, GupService gupService,
                          SicknessService sicknessService, AnimalService animalService) {
        this.octopodRepository = octopodRepository;
        this.userRepository = userRepository;
        this.crewService = crewService;
        this.medicineService = medicineService;
        this.gupService = gupService;
        this.sicknessService = sicknessService;
        this.animalService = animalService;
    }

    public Octopod initOctopod(User user){
        Octopod octopod = new Octopod();
        octopodRepository.save(octopod);
        octopod.setGupList(gupService.initGups(octopod));
        octopod.setMedicineList(medicineService.initMedicineStock(octopod));
        octopod.setCrewMemberList(crewService.initCrew(octopod));
        octopodRepository.save(octopod);
        return octopod;
    }

    public Octopod save(Octopod octopod){
        return octopodRepository.save(octopod);
    }

    public int recalculatePoints(User user) {
        if (octopodRepository.findByUser(user).isPresent()){
            Octopod octopod = octopodRepository.findByUser(user).get();
            int total = MedicinePrices.START_MEDICINESTOCK_PRICE;
            total -= gupService.pointsPaidForGups(octopod);
            total -= medicineService.pointsPaidForMedicines(octopod);
            total -= crewService.pointsPaidForCrew(octopod);
            if (!user.getPatientTreatedList().isEmpty()){
                total += animalService.pointsForCure(user);
            }
            user.setPoints(total);
            userRepository.save(user);
            return total;
        }
        return 0;
    }

    public UserAndPoint udatePoints(User user) {
        UserAndPoint userAndPoint = new UserAndPoint();
        userAndPoint.setUsername(user.getUsername());
        int actualPoints = recalculatePoints(user);
        userAndPoint.setPoints(actualPoints);
        return userAndPoint;
    }

    public OctopodDTO createOctopodDTO(Octopod octopod, User user) {
        CrewDTO crewDTO = crewService.createCrewList(octopod);
        GupListDTO gupListDTO = gupService.createGupList(octopod);
        MedicineStockDTO medicineStockDTO = medicineService.createMedicineList(octopod);
        PatinentListDTO patinentListDTO = animalService.createUnderTreatmentList(user);
        UserAndPoint userAndPoint = udatePoints(user);
        return  new OctopodDTO(crewDTO, gupListDTO, medicineStockDTO, patinentListDTO, userAndPoint);
    }

    public Octopod findOctopodByUser(User user) {
        return octopodRepository.findOctopodByUser(user).orElse(null);
    }

    public PatientDTO startCure(Animal animal, User user) {
        PatientDTO patientDTO =  animalService.startCure(animal, user);
        Octopod octopod = user.getOctopod();
        octopod.setMedicineList(deleteUsedMedicines(animal.getSickness().getCureList(), octopod.getMedicineList()));
        octopodRepository.save(octopod);
        return patientDTO;
    }

    private List<Medicine> deleteUsedMedicines(List<Cure> cureList, List<Medicine> medicineList) {
        List<MedicineType> medicineTypesOctopodHas = cureList
                .stream()
                .map(cure -> cure.getType())
                .collect(Collectors.toList());
        List<MedicineType> medicineTypesNeeded = medicineList
                .stream()
                .map(cure -> cure.getType())
                .collect(Collectors.toList());
        medicineTypesOctopodHas.removeAll(medicineTypesNeeded);

        List<Medicine> newMedicineList = medicineTypesOctopodHas
                .stream()
                .map(medicineType -> new Medicine(medicineType))
                .collect(Collectors.toList());

        return newMedicineList;
    }

}
