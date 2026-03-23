package com.thexeler.whin.repository;

import com.thexeler.whin.entity.rules.system.Squad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadRepository extends JpaRepository<Squad, Long> {
}
