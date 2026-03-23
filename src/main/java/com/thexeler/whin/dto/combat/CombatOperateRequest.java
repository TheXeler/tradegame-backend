package com.thexeler.whin.dto.combat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CombatOperateRequest {
    private String token;
    private Long combatId;
    private String operation;
    private String data;
}
