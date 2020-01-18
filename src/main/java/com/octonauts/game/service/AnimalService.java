package com.octonauts.game.service;

import com.octonauts.game.model.dto.PatientDTO;
import com.octonauts.game.model.dto.PatinentListDTO;
import com.octonauts.game.model.dto.SicknessDTO;
import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.model.entity.cureFactory.Cure;
import com.octonauts.game.model.entity.sicknessFactory.Sickness;
import com.octonauts.game.model.enums.AnimalType;
import com.octonauts.game.model.enums.MedicineType;
import com.octonauts.game.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

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

    public PatientDTO createNewPatient() {
        Animal animal = new Animal();
        PatientDTO patientDTO;
        Sickness sickness = sicknessService.createNewSickness();
        animal.setSickness(sickness);
        animal.setPointsGivenForCure(sickness.getLevel() * 3);
        animal.setType(randomAnimalTypeGenerator());
        sickness.setAnimal(animalRepository.save(animal));
        sicknessService.save(sickness);
        patientDTO = createPatientDTO(animal);
        return patientDTO;
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
            if (i == randomNumber){
                return animalType;
            }
            i++;
        }
        return AnimalType.ORCA;
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
}
