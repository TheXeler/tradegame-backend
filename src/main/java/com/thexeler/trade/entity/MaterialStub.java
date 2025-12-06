package com.thexeler.trade.entity;

import com.thexeler.trade.kits.enums.MaterialType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "stubs_material")
public class MaterialStub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MaterialType materialType;
    private String transactionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;


    private Long price;
    private Long amount;

    private LocalDateTime createdTime;
}
