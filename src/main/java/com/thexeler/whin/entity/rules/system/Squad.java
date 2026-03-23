package com.thexeler.whin.entity.rules.system;

import com.thexeler.whin.entity.rules.army.Armor;
import com.thexeler.whin.entity.rules.army.Race;
import com.thexeler.whin.entity.rules.army.Train;
import com.thexeler.whin.entity.rules.army.Weapon;
import com.thexeler.whin.entity.userdata.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Getter
@Setter
@Entity
@Table(name = "squads")
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "number", nullable = false)
    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armor_id", nullable = false)
    private Armor armor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "squad_weapons",
        joinColumns = @JoinColumn(name = "squad_id"),
        inverseJoinColumns = @JoinColumn(name = "melee_weapon_id")
    )
    private List<Weapon> weapons = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "squad_projectile",
        joinColumns = @JoinColumn(name = "squad_id")
    )
    @MapKeyJoinColumn(name = "projectile_id")
    @Column(name = "ammo_count")
    private Map<Weapon, Integer> projectiles = new HashMap<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "squad_trains",
        joinColumns = @JoinColumn(name = "squad_id"),
        inverseJoinColumns = @JoinColumn(name = "train_id")
    )
    private List<Train> trains = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}
