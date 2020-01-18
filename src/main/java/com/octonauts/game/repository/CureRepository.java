package com.octonauts.game.repository;

import com.octonauts.game.model.entity.cureFactory.Cure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CureRepository extends CrudRepository<Cure, Long> {
}
