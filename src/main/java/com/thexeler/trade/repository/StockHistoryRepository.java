package com.thexeler.trade.repository;

import com.thexeler.trade.entity.Company;
import com.thexeler.trade.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findAllByCompany(Company company);
    List<StockHistory> findAllByCompanyAndRecordTimeBetween(Company company, LocalDateTime start, LocalDateTime end);
}
