package com.thexeler.whin.entity.rules.army;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "features")
public class Features {

    @Id
    @Column(name = "feature_id", nullable = false, unique = true)
    private String featureId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "script")
    private String script;
}
