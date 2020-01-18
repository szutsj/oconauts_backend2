package com.octonauts.game.service;

import com.octonauts.game.contsants.MedicinePrices;
import com.octonauts.game.model.dto.UserAndPoint;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.repository.OctopodRepository;
import com.octonauts.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
