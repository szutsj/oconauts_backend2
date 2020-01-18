package com.octonauts.game.repository;

import com.octonauts.game.model.entity.CrewMember;
import com.octonauts.game.model.entity.Octopod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewRepository extends CrudRepository<CrewMember, Long> {
    List<CrewMember> findAllByOctopod(Octopod octopod);
    @Query("SELECT SUM(c.pointsForActivate) FROM CrewMember c WHERE c.octopod = ?1 " +
            "AND c.active = 1")
    Integer countPointForActivate(@Param("octopod") Octopod octopod);
}
