package com.octonauts.game.scheduler;


import com.octonauts.game.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledConfiguration {

    AnimalService animalService;

    @Autowired
    public ScheduledConfiguration(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Scheduled(fixedRate = 1000)
    public void generateNewPatientsScheduler() {
        animalService.generatePatients();
    }

}