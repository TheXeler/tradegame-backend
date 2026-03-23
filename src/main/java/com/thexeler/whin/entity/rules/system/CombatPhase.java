
package com.thexeler.whin.entity.rules.system;

public enum CombatPhase {
    CREATE,
    PREPARE,
    DEPLOY,
    COMMAND,
    MOVE,
    REINFORCEMENT,
    SHOOT,
    CHARGE,
    MELEE,
    END;

    public static CombatPhase nextPhase(CombatPhase currentPhase){
        return switch (currentPhase) {
            case CREATE -> PREPARE;
            case PREPARE -> DEPLOY;
            case DEPLOY, MELEE -> COMMAND;
            case COMMAND -> MOVE;
            case MOVE -> REINFORCEMENT;
            case REINFORCEMENT -> SHOOT;
            case SHOOT -> CHARGE;
            case CHARGE -> MELEE;
            case END -> END;
        };
    }
}