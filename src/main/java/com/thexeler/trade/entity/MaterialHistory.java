package com.thexeler.trade.entity;

import com.thexeler.trade.kits.enums.MaterialType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "history_material")
public class MaterialHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type")
    private MaterialType materialType;

    private LocalDateTime recordTime;
    private Long price;
    private Long quantity;
}
