package com.thexeler.whin.entity.rules.army;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(name = "vehicle_id", nullable = false, unique = true)
    private String vehicleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "speed", nullable = false)
    private int speed;

    @Column(name = "armor", nullable = false)
    private int armor;

    @Column(name = "hp", nullable = false)
    private int hp;

    @Column(name = "tax_cost", nullable = false)
    private int taxCost;

    @Column(name = "alloy_cost", nullable = false)
    private int alloyCost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "vehicle_features",
        joinColumns = @JoinColumn(name = "vehicle_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<Features> features = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "vehicle_weapons",
        joinColumns = @JoinColumn(name = "vehicle_id"),
        inverseJoinColumns = @JoinColumn(name = "weapon_id")
    )
    private List<Weapon> weapons = new ArrayList<>();
}
