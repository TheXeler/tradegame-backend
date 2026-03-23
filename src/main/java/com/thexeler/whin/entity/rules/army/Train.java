package com.thexeler.whin.entity.rules.army;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "trains")
public class Train {

    @Id
    @Column(name = "train_id", nullable = false, unique = true)
    private String trainId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name = "target", nullable = false)
    private String target;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "granted_feature_id")
    private Features grantedFeature;
}
