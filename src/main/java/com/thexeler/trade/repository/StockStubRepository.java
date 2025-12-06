package com.thexeler.trade.repository;

import com.thexeler.trade.entity.StockStub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockStubRepository extends JpaRepository<StockStub, Long> {
}
