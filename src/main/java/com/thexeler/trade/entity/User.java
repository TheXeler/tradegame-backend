package com.thexeler.trade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String nickname;
    private String password;
    private String lastToken;

    private boolean isAdmin;

    // Game Data
    private long balance;
    private long laborPoints;
    private long factionCash;
    // Materials
    private long hardwareMaterials;
    private long buildingMaterials;
    private long electricalMaterials;
    private long chemicalMaterials;
    // Others
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Map<String, StockStub> stocks;

    public long getGameValue(String key) {
        return switch (key) {
            case "balance" -> balance;
            case "laborPoints" -> laborPoints;
            case "factionCash" -> factionCash;
            case "hardwareMaterials" -> hardwareMaterials;
            case "buildingMaterials" -> buildingMaterials;
            case "electricalMaterials" -> electricalMaterials;
            case "chemicalMaterials" -> chemicalMaterials;
            default -> 0;
        };
    }

    public Map<String, Long> getAllGameValue() {
        return Map.of(
                "balance", balance,
                "laborPoints", laborPoints,
                "factionCash", factionCash,
                "hardwareMaterials", hardwareMaterials,
                "buildingMaterials", buildingMaterials,
                "electricalMaterials", electricalMaterials,
                "chemicalMaterials", chemicalMaterials
        );
    }

    public void setGameValue(String key, long value) {
        switch (key) {
            case "balance":
                this.balance = value;
                break;
            case "laborPoints":
                this.laborPoints = value;
                break;
            case "factionCash":
                this.factionCash = value;
                break;
            case "hardwareMaterials":
                this.hardwareMaterials = value;
                break;
            case "buildingMaterials":
                this.buildingMaterials = value;
                break;
            case "electricalMaterials":
                this.electricalMaterials = value;
                break;
            case "chemicalMaterials":
                this.chemicalMaterials = value;
                break;
        }
    }
}
