package com.octonauts.game.repository;

import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long> {
    Animal save(Animal animal);
    List<Animal> findAnimalsByTreatmentStartedAtIsNull();
    List<Animal> findAnimalsByUser(User user);
    @Query("SELECT SUM(a.pointsGivenForCure) FROM Animal a WHERE a.user = ?1 " +
            "AND a.treatmentStartedAt IS NOT NULL " +
            "AND a.treatmentFinishedAt < ?2")
    Integer countCurePoints(User user, LocalDateTime current);

}
