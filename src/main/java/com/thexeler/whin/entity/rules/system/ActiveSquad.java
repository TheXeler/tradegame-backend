package com.thexeler.whin.entity.rules.system;

import com.thexeler.whin.entity.rules.army.Armor;
import com.thexeler.whin.entity.rules.army.Features;
import com.thexeler.whin.entity.rules.army.Race;
import com.thexeler.whin.entity.rules.army.Train;
import com.thexeler.whin.entity.rules.army.Weapon;
import com.thexeler.whin.entity.userdata.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "active_squads")
public class ActiveSquad {

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
            name = "active_squad_weapons",
            joinColumns = @JoinColumn(name = "active_squad_id"),
            inverseJoinColumns = @JoinColumn(name = "weapon_id")
    )
    private List<Weapon> weapons = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "active_squad_projectiles",
            joinColumns = @JoinColumn(name = "active_squad_id")
    )
    @MapKeyJoinColumn(name = "projectile_id")
    @Column(name = "ammo_count")
    private Map<Weapon, Integer> projectiles = new HashMap<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "active_squad_trains",
            joinColumns = @JoinColumn(name = "active_squad_id"),
            inverseJoinColumns = @JoinColumn(name = "train_id")
    )
    private List<Train> trains = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_id")
    private Combat combat;

    @Column(name = "current_hp", nullable = false)
    private int currentHp;

    @Column(name = "is_acted", nullable = false)
    private boolean isActed = false;

    @Column(name = "pos_x", nullable = false)
    private int posX = 0;

    @Column(name = "pos_y", nullable = false)
    private int posY = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "activeSquad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActiveSquadEffect> effects = new ArrayList<>();

    @Getter
    @Setter
    @Entity
    @Table(name = "active_squad_effects")
    public static class ActiveSquadEffect {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "active_squad_id", nullable = false)
        private ActiveSquad activeSquad;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "feature_id", nullable = false)
        private Features features;

        @Column(name = "duration", nullable = false)
        private int duration;
    }

    public boolean isInCombat() {
        return posX >= 0 && posY >= 0;
    }
}
