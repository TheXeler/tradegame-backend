package com.thexeler.whin.entity.rules.army;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "weapons")
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type", nullable = false)
    private WeaponType weaponType;

    @Column(name = "range", nullable = false)
    private Integer range;

    @Column(name = "rate_of_fire")
    private Integer rateOfFire;

    @Column(name = "accuracy")
    private Integer accuracy;

    @Column(name = "damage")
    private Integer damage;

    @Column(name = "armor_piercing")
    private Integer armorPiercing;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "cost")
    private Integer cost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "weapon_features",
        joinColumns = @JoinColumn(name = "weapon_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<Features> features = new ArrayList<>();
}
