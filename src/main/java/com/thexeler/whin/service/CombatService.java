package com.thexeler.whin.service;

import com.thexeler.whin.dto.combat.CombatOperateRequest;
import com.thexeler.whin.entity.rules.system.Combat;
import com.thexeler.whin.entity.rules.system.CombatPhase;
import com.thexeler.whin.entity.userdata.User;
import com.thexeler.whin.dto.combat.CombatOperate;
import com.thexeler.whin.repository.CombatRepository;
import com.thexeler.whin.repository.SquadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CombatService {

    @Autowired
    private CombatRepository combatRepository;

    @Autowired
    private SquadRepository squadRepository;

    private final Map<Long, Combat> activeCombats = new ConcurrentHashMap<>();

    @Transactional
    public Combat loadCombat(Long combatId) {
        return activeCombats.computeIfAbsent(combatId, id -> {
            Optional<Combat> combatOpt = combatRepository.findById(id);

            // TODO : create
            return combatOpt.orElseGet(Combat::new);
        });
    }

    @Transactional
    public void unloadCombat(Long combatId) {
        Combat combat = activeCombats.get(combatId);
        if (combat != null) {
            combat.setCombatPhase(CombatPhase.END);
            combatRepository.save(combat);
            activeCombats.remove(combatId);
        }
    }

    @Transactional
    public Map<String, Object> executeOperate(Long combatId, User player, CombatOperateRequest request) {
        Combat combat = loadCombat(combatId);
        if (combat == null) {
            return Map.of("success", false, "message", "战斗不存在");
        }

        if (!combat.isCurrentPlayer(player)) {
            return Map.of("success", false, "message", "不是你的回合");
        }

        CombatOperate operate = new CombatOperate(request);

        try {
            Map<String, Object> result = switch (operate.getOperateType()) {
                case MOVE_SQUAD -> handleMoveSquad(combat, player, operate);
                case ATTACK_SQUAD -> handleAttackSquad(combat, player, operate);
                case DEPLOY_REINFORCEMENT -> handleDeployReinforcement(combat, player, operate);
                case END_PHASE -> handleEndPhase(combat, player, operate);
                case USE_FEATURE -> handleUseFeature(combat, player, operate);
                case USE_COMMAND -> handleUseCommand(combat, player, operate);
            };

            if ((Boolean) result.get("success")) {
                combatRepository.save(combat);
            }

            return result;
        } catch (Exception e) {
            return Map.of("success", false, "message", "指令执行失败：" + e.getMessage());
        }
    }

    private Map<String, Object> handleUseCommand(Combat combat, User player, CombatOperate operate) {
        return Map.of("success", false, "message", "开发中");
    }

    private Map<String, Object> handleUseFeature(Combat combat, User player, CombatOperate operate) {
        return Map.of("success", false, "message", "开发中");
    }

    private Map<String, Object> handleMoveSquad(Combat combat, User player, CombatOperate command) {
        if (combat.getCombatPhase() != CombatPhase.MOVE) {
            return Map.of("success", false, "message", "当前不是移动阶段");
        }

        Long squadId = command.getSquadId();
        if (squadId == null) {
            return Map.of("success", false, "message", "需要指定小队 ID");
        }

        if (!combat.getActiveSquads().containsKey(squadId) &&
                (combat.getActiveSquads().get(squadId).getPosX() < 0 || combat.getActiveSquads().get(squadId).getPosY() < 0)) {
            return Map.of("success", false, "message", "小队不在战场上");
        }

        if (!combat.isSquadOwner(squadId, player)) {
            return Map.of("success", false, "message", "你不是该小队的拥有者");
        }

        if (combat.getActiveSquads().get(squadId).isActed()) {
            return Map.of("success", false, "message", "该小队本回合已行动过");
        }

        combat.moveSquad(squadId, command.getPosX(), command.getPosY());

        return Map.of("success", true, "message", "小队移动成功", "data", Map.of(
                "squadId", squadId,
                "parameters", command.getParameters()
        ));
    }

    private Map<String, Object> handleAttackSquad(Combat combat, User player, CombatOperate command) {
        CombatPhase currentPhase = combat.getCombatPhase();
        if (currentPhase != CombatPhase.SHOOT &&
                currentPhase != CombatPhase.CHARGE &&
                currentPhase != CombatPhase.MELEE) {
            return Map.of("success", false, "message", "当前不是攻击阶段");
        }

        Long squadId = command.getSquadId();
        if (squadId == null) {
            return Map.of("success", false, "message", "需要指定小队 ID");
        }

        if (!combat.getActiveSquads().containsKey(squadId) && !combat.getActiveSquads().get(squadId).isInCombat()) {
            return Map.of("success", false, "message", "小队不在战场上");
        }

        if (!combat.isSquadOwner(squadId, player)) {
            return Map.of("success", false, "message", "你不是该小队的拥有者");
        }

        if (!combat.getActiveSquads().get(squadId).isActed()) {
            return Map.of("success", false, "message", "该小队本回合已行动过");
        }

        // TODO:Attack Logic

        return Map.of("success", true, "message", "攻击执行成功", "data", Map.of(
                "squadId", squadId,
                "parameters", command.getParameters()
        ));
    }

    private Map<String, Object> handleDeployReinforcement(Combat combat, User player, CombatOperate command) {
        if (!combat.isCurrentPlayer(player)) {
            return Map.of("success", false, "message", "不是你的回合");
        }

        if (combat.getCombatPhase() != CombatPhase.REINFORCEMENT) {
            return Map.of("success", false, "message", "当前不是援军部署阶段");
        }

        return Map.of("success", true, "message", "援军部署成功", "data", Map.of(
                "parameters", command.getParameters()
        ));
    }

    private Map<String, Object> handleEndPhase(Combat combat, User player, CombatOperate command) {
        if (!combat.isCurrentPlayer(player)) {
            return Map.of("success", false, "message", "不是你的回合");
        }

        String phaseName = (String) command.getParameters().get("phase");
        if (phaseName == null) {
            return Map.of("success", false, "message", "需要指定阶段名称");
        }

        CombatPhase newPhase;
        try {
            newPhase = CombatPhase.valueOf(phaseName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Map.of("success", false, "message", "无效的阶段名称");
        }

        combat.nextPhase();

        return Map.of("success", true, "message", "阶段切换成功", "data", Map.of(
                "newPhase", newPhase.name()
        ));
    }
}
