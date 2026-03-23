package com.thexeler.whin.repository;

import com.thexeler.whin.entity.rules.system.Combat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CombatRepository extends JpaRepository<Combat, Long> {
}
