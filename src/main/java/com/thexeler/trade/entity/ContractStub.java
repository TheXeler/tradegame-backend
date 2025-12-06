package com.thexeler.trade.entity;

import com.thexeler.trade.kits.enums.MaterialType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "contract_stub")
public class ContractStub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MaterialType materialType;

    private Integer quantity;
    private Double amount;


    private String buyer;
    private String seller;

    private LocalDateTime expiry;
    private LocalDateTime createdAt;
}
