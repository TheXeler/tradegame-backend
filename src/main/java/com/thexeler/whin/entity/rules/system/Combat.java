package com.thexeler.whin.entity.rules.system;

import com.thexeler.whin.entity.userdata.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "combats")
public class Combat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "duration")
    private int duration;

    @Column(name = "tax_bounty")
    private int taxBounty;

    @Column(name = "alloy_bounty")
    private int alloyBounty;

    @Column(name = "map_config", length = 1000)
    private String mapConfig;

    @Enumerated(EnumType.STRING)
    @Column(name = "combat_phase", nullable = false)
    private CombatPhase combatPhase = CombatPhase.DEPLOY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_player_id")
    private User currentPlayer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "combat_squads",
            joinColumns = @JoinColumn(name = "combat_id"),
            inverseJoinColumns = @JoinColumn(name = "squad_id")
    )
    private List<Squad> squads = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "combat_players",
            joinColumns = @JoinColumn(name = "combat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> players = new HashSet<>();

    @Column(name = "round_number", nullable = false)
    private int roundNumber = 0;

    @Column(name = "is_finished", nullable = false)
    private boolean isFinished = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "combat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActiveSquad> activeSquadsList = new ArrayList<>();

    @Transient
    private Map<Long, ActiveSquad> activeSquads = new HashMap<>();

    @PostLoad
    public void onLoad() {
        if (activeSquadsList != null) {
            for (ActiveSquad squad : activeSquadsList) {
                activeSquads.put(squad.getId(), squad);
            }
        }
    }

    public void nextPhase() {
        combatPhase = CombatPhase.nextPhase(combatPhase);
        if (combatPhase == CombatPhase.COMMAND) {
            currentPlayer = players.stream().filter(player -> !player.equals(currentPlayer)).findFirst().orElse(null);
        }
    }

    public void moveSquad(Long activeSquadId, int x, int y) {
        activeSquads.get(activeSquadId).setPosX(x);
        activeSquads.get(activeSquadId).setPosY(y);
    }

    public boolean isCurrentPlayer(User user) {
        return user.equals(currentPlayer);
    }

    public boolean isSquadOwner(Long squadId, User player) {
        return activeSquads.get(squadId).getOwner().equals(player);
    }
}