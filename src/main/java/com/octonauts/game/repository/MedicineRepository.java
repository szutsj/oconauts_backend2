package com.octonauts.game.repository;

import com.octonauts.game.model.entity.Medicine;
import com.octonauts.game.model.entity.Octopod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Long> {
    List<Medicine> findAllByOctopodAndUsedIsFalse(Octopod octopod);
    @Query("SELECT SUM(m.price) FROM Medicine m WHERE m.octopod = ?1")
    Integer countPrices(@Param("octopod") Octopod octopod);
}
