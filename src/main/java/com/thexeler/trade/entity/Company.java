package com.thexeler.trade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "controller_id")
    private User controller;

    private String name;

    private String description;
    private Long marketValue;
    private Boolean isJointStock;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<StockHistory> stockHistories = new ArrayList<>();

    private LocalDate createdAt;
}

