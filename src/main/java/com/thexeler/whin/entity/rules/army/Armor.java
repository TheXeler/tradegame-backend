package com.thexeler.whin.entity.rules.army;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "armors")
public class Armor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "protection_level", nullable = false)
    private int protectionLevel;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "cost")
    private int cost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "armor_features",
        joinColumns = @JoinColumn(name = "armor_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<Features> features = new ArrayList<>();
}
