package com.thexeler.whin.entity.rules.system;

import com.thexeler.whin.entity.rules.army.Features;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "commands")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cp_cost", nullable = false)
    private int cpCost;

    @Column(name = "trigger_timing", columnDefinition = "TEXT")
    private String triggerTiming;

    @Column(name = "target_scope", columnDefinition = "TEXT")
    private String targetScope;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "effect_feature_id", nullable = false)
    private Features effectFeature;
}
