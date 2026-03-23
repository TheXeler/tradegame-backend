package com.thexeler.whin.entity.rules.army;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "races")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "hp", nullable = false)
    private int hp;

    @Column(name = "speed", nullable = false)
    private int speed;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name = "weapon_weight_limit", nullable = false)
    private Integer weaponWeightLimit;
    @Column(name = "projectile_weight_limit", nullable = false)
    private Integer projectileWeightLimit;
    @Column(name = "armor_weight_limit", nullable = false)
    private Integer armorWeightLimit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "race_features",
        joinColumns = @JoinColumn(name = "race_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<Features> features = new ArrayList<>();
}
