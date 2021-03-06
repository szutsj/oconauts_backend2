package com.octonauts.game.service;

import com.octonauts.game.model.dto.PatientDTO;
import com.octonauts.game.model.dto.PatinentListDTO;
import com.octonauts.game.model.dto.SicknessDTO;
import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.model.entity.cureFactory.Cure;
import com.octonauts.game.model.entity.medicineFactory.Medicine;
import com.octonauts.game.model.entity.sicknessFactory.Sickness;
import com.octonauts.game.model.enums.AnimalType;
import com.octonauts.game.model.enums.MedicineType;
import com.octonauts.game.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    public static final int MAX_PATIENT_NUMBER = 10;
    private AnimalRepository animalRepository;
    private SicknessService sicknessService;

    @Autowired
    public AnimalService(AnimalRepository animalRepository, SicknessService sicknessService) {
        this.animalRepository = animalRepository;
        this.sicknessService = sicknessService;
    }

    public Optional<Animal> findById(Long id){
        return animalRepository.findById(id);
    }

    public PatinentListDTO createWaitingPatientList() {
        List<PatientDTO> patientList = new ArrayList<>();
        List<Animal> waitingAnimals = animalRepository.findAnimalsByTreatmentStartedAtIsNull();
        for (Animal animal: waitingAnimals){
            patientList.add(createPatientDTO(animal));
        }
        return new PatinentListDTO(patientList);
    }

    public PatinentListDTO createUnderTreatmentList(User user) {
        List<PatientDTO> patientList = new ArrayList<>();
        List<Animal> animalsUnderTreatmentByUser = animalRepository.findAnimalsByUser(user);
        for (Animal animal: animalsUnderTreatmentByUser){
            if (animal.getTreatmentFinishedAt() != null && animal.getTreatmentFinishedAt().isAfter(LocalDateTime.now())){
                patientList.add(createPatientDTO(animal));
            }
        }
        return new PatinentListDTO(patientList);
    }

    public void createNewPatient() {
        Animal animal = new Animal();
        animal = animalRepository.save(animal);
        Sickness sickness = sicknessService.createNewSickness();
        sickness.setAnimal(animal);
        animal.setSickness(sickness);
        animal.setPointsGivenForCure(sickness.getLevel());
        animal.setType(randomAnimalTypeGenerator());
        sicknessService.save(sickness);
        animalRepository.save(animal);
    }

    public PatientDTO startCure(Animal animal, User user){
        animal.setTreatmentStartedAt(LocalDateTime.now());
        animal.setTreatmentFinishedAt(LocalDateTime.now().plusMinutes((animal.getSickness().getLevel() * 3)));
        animal.setUser(user);
        animalRepository.save(animal);
        return createPatientDTO(animal);
    }

    public AnimalType randomAnimalTypeGenerator(){
        int length = AnimalType.values().length;
        int randomNumber = (int)(Math.random() * length);
        int i = 0;
        for (AnimalType animalType : AnimalType.values()){
            if (i == randomNumber && notExistAmongActivePatients(animalType)){
                return animalType;
            }
            i++;
        }
        return AnimalType.ORCA;
    }

    private boolean notExistAmongActivePatients(AnimalType animalType) {
        List<Animal> existingAnimals = (List<Animal>) animalRepository.findAll();
        Predicate<Animal> hasThisType = animal -> animalType.equals(animal.getType());
        boolean result =  existingAnimals.stream()
                .filter(animal -> animal.getTreatmentFinishedAt().equals(null)
                        || animal.getTreatmentFinishedAt().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList())
                .stream().noneMatch(hasThisType);
        return result;
    }

    public PatientDTO createPatientDTO(Animal animal){
        PatientDTO patientDTO = new PatientDTO();
        if (animal != null) {
            patientDTO.setId(animal.getId());
            patientDTO.setType(animal.getType());
            if (animal.getTreatmentStartedAt() != null){
                patientDTO.setTreatmentStartedAt(Timestamp.valueOf(animal.getTreatmentStartedAt()));
            }
            if (animal.getTreatmentFinishedAt() != null){
                patientDTO.setTreatmentFinishedAt(Timestamp.valueOf(animal.getTreatmentFinishedAt()));
            }
            patientDTO.setPointsGivenForCure(animal.getPointsGivenForCure());
            patientDTO.setSicknessDTO(createSicknessDTO(animal.getSickness()));
        }
        return patientDTO;
    }

    public SicknessDTO createSicknessDTO(Sickness sickness){
        SicknessDTO sicknessDTO = new SicknessDTO();
        if (sickness != null){
            List<MedicineType> cureForSickness = new ArrayList<>();
            for(Cure cure : sickness.getCureList()){
                cureForSickness.add(cure.getType());
            }
            sicknessDTO.setMedicinesNeeded(cureForSickness);
            sicknessDTO.setType(sickness.getType());
            sicknessDTO.setLevel(sickness.getLevel());
        }
        return sicknessDTO;
    }

    public int pointsForCure(User user) {
        if (animalRepository.countCurePoints(user, LocalDateTime.now()) == null){
            return 0;
        }
        return animalRepository.countCurePoints(user, LocalDateTime.now());
    }

    @Scheduled(fixedDelay = 1000)
    public void generatePatients() {
        if (lessPatientThanMax()){
            createNewPatient();
        }
    }

    private boolean lessPatientThanMax(){
        int notCuredPatients =  animalRepository.countNotYetCuredPatients(LocalDateTime.now());
        return MAX_PATIENT_NUMBER  > notCuredPatients;
    }

    public boolean checkIfOctopodHasAllMedicinesNeededForCure(List<Cure> cureList, List<Medicine> medicineList) {
        List<MedicineType> medicineTypesOctopodHas = cureList
                .stream()
                .map(cure -> cure.getType())
                .collect(Collectors.toList());
        List<MedicineType> medicineTypesNeeded = medicineList
                .stream()
                .map(cure -> cure.getType())
                .collect(Collectors.toList());
        medicineTypesNeeded.removeAll(medicineTypesOctopodHas);

        return medicineTypesNeeded.isEmpty();
    }
}
