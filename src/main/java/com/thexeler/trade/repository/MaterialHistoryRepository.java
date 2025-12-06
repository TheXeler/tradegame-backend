package com.thexeler.trade.repository;

import com.thexeler.trade.entity.MaterialHistory;
import com.thexeler.trade.kits.enums.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MaterialHistoryRepository extends JpaRepository<MaterialHistory, Long> {
    List<MaterialHistory> findAllByMaterialType(MaterialType materialType);
    List<MaterialHistory> findAllByMaterialTypeAndRecordTimeBetween(MaterialType materialType, LocalDateTime start, LocalDateTime end);
}
