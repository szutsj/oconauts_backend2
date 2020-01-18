package com.octonauts.game.repository;

import com.octonauts.game.model.entity.sicknessFactory.Sickness;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SicknessRepository extends CrudRepository<Sickness, Long> {
}
