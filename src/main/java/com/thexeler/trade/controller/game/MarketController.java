package com.thexeler.trade.controller.game;

import com.thexeler.trade.entity.Company;
import com.thexeler.trade.entity.MaterialHistory;
import com.thexeler.trade.entity.StockHistory;
import com.thexeler.trade.kits.enums.MaterialType;
import com.thexeler.trade.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game/market")
@CrossOrigin
public class MarketController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MaterialStubRepository materialStubRepository;
    @Autowired
    private MaterialHistoryRepository materialHistoryRepository;
    @Autowired
    private StockStubRepository stockStubRepository;
    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @GetMapping("/getMaterialList")
    public Map<String, Object> getMaterialList() {
        return Map.of("message", "Success", "data", List.of(MaterialType.values()));
    }

    @GetMapping("/getStockList")
    public Map<String, Object> getStockList() {
        List<Company> jointStockCompanies = companyRepository.findAll().stream()
                .filter(Company::getIsJointStock)
                .toList();
        return Map.of("message", "Success", "data", jointStockCompanies);
    }

    @GetMapping("/getDayStockInfo")
    public Map<String, Object> getDayStockInfo(@RequestParam Long companyId) {
        return companyRepository.findById(companyId)
                .map(company -> {
                    LocalDateTime todayStart = LocalDate.now().atStartOfDay();
                    LocalDateTime todayEnd = todayStart.plusDays(1);
                    List<StockHistory> histories = stockHistoryRepository
                            .findAllByCompanyAndRecordTimeBetween(company, todayStart, todayEnd);
                    return Map.of("message", "Success", "data", histories);
                })
                .orElse(Map.of("message", "Company not found"));
    }

    @GetMapping("/getWeekStockInfo")
    public Map<String, Object> getWeekStockInfo(@RequestParam Long companyId) {
        return companyRepository.findById(companyId)
                .map(company -> {
                    LocalDateTime weekStart = LocalDate.now().minusDays(7).atStartOfDay();
                    LocalDateTime now = LocalDateTime.now();
                    List<StockHistory> histories = stockHistoryRepository
                            .findAllByCompanyAndRecordTimeBetween(company, weekStart, now);
                    return Map.of("message", "Success", "data", histories);
                })
                .orElse(Map.of("message", "Company not found"));
    }

    @GetMapping("/getDayMaterialInfo")
    public Map<String, Object> getDayMaterialInfo(@RequestParam MaterialType materialType) {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);
        List<MaterialHistory> histories = materialHistoryRepository
                .findAllByMaterialTypeAndRecordTimeBetween(materialType, todayStart, todayEnd);
        return Map.of("message", "Success", "data", histories);
    }

    @GetMapping("/getWeekMaterialInfo")
    public Map<String, Object> getWeekMaterialInfo(@RequestParam MaterialType materialType) {
        LocalDateTime weekStart = LocalDate.now().minusDays(7).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        List<MaterialHistory> histories = materialHistoryRepository
                .findAllByMaterialTypeAndRecordTimeBetween(materialType, weekStart, now);
        return Map.of("message", "Success", "data", histories);
    }
}
