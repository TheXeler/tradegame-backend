package com.thexeler.whin.entity.userdata;

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
    private int tax;
    private int alloy;
    private int bountyTax;
    private int bountyAlloy;
    private int combatId;
    // Game Summary
    private int missionSummary;
    private int missionSummaryTax;
    private int missionSummaryAlloy;
    private int missionSummaryWin;
    private int missionSummaryLose;

    public int getGameValue(String key) {
        return switch (key) {
            case "tax" -> tax;
            case "alloy" -> alloy;
            case "bountyTax" -> bountyTax;
            case "bountyAlloy" -> bountyAlloy;
            case "combatId" -> combatId;
            case "missionSummary" -> missionSummary;
            case "missionSummaryTax" -> missionSummaryTax;
            case "missionSummaryAlloy" -> missionSummaryAlloy;
            case "missionSummaryWin" -> missionSummaryWin;
            case "missionSummaryLose" -> missionSummaryLose;
            default -> 0;
        };
    }

    public Map<String, Integer> getAllGameValue() {
        return Map.of(
                "tax", tax,
                "alloy", alloy,
                "bountyTax", bountyTax,
                "bountyAlloy", bountyAlloy,
                "combatId", combatId,
                "missionSummary", missionSummary,
                "missionSummaryTax", missionSummaryTax,
                "missionSummaryAlloy", missionSummaryAlloy,
                "missionSummaryWin", missionSummaryWin,
                "missionSummaryLose", missionSummaryLose
        );
    }

    public void setGameValue(String key, int value) {
        switch (key) {
            case "tax":
                tax = value;
                break;
            case "alloy":
                alloy = value;
                break;
            case "bountyTax":
                bountyTax = value;
                break;
            case "bountyAlloy":
                bountyAlloy = value;
                break;
            case "combatId":
                combatId = value;
                break;
            case "missionSummary":
                missionSummary = value;
                break;
            case "missionSummaryTax":
                missionSummaryTax = value;
            case "missionSummaryAlloy":
                missionSummaryAlloy = value;
                break;
            case "missionSummaryWin":
                missionSummaryWin = value;
                break;
            case "missionSummaryLose":
                missionSummaryLose = value;
                break;
        }
    }
}
