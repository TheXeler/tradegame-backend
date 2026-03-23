package com.thexeler.whin.repository;

import com.thexeler.whin.entity.rules.system.ActiveSquad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiveSquadRepository extends JpaRepository<ActiveSquad, Long> {

    @Query("SELECT a FROM ActiveSquad a JOIN FETCH a.race JOIN FETCH a.armor JOIN FETCH a.owner WHERE a.combat.id = :combatId")
    List<ActiveSquad> findByCombatId(@Param("combatId") Long combatId);
}
