package com.octonauts.game.repository;

import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OctopodRepository extends CrudRepository<Octopod, Long> {
    Optional<Octopod> findByUser(User user);
    Optional<Octopod> findOctopodByUser(User name);
}
