package com.octonauts.game.repository;

import com.octonauts.game.model.entity.Gup;
import com.octonauts.game.model.entity.Octopod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GupRepository extends CrudRepository<Gup, Long> {
    List<Gup> findAllByOctopod(Octopod octopod);
    @Query("SELECT SUM(g.pointsForActivate) FROM Gup g WHERE g.octopod = ?1 " +
     "AND g.active = 1")
    Integer countPointForActivate(@Param("octopod") Octopod octopod);

    @Override
    Optional<Gup> findById(Long gupId);
}
